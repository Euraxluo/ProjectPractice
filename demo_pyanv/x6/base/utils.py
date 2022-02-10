"""
Utilities
-------

helper functions.

"""

import base64
import json
import math
import os
import struct
import zlib

# from jinja2 import Environment, PackageLoader

try:
    import pandas as pd
except ImportError:
    pd = None

try:
    import numpy as np
except ImportError:
    np = None


rootpath = os.path.abspath(os.path.dirname(__file__))


def get_templates():
    """Get Jinja templates."""
    return Environment(loader=PackageLoader('branca', 'templates'))


def legend_scaler(legend_values, max_labels=10.0):
    """
    Downsamples the number of legend values so that there isn't a collision
    of text on the legend colorbar (within reason). The colorbar seems to
    support ~10 entries as a maximum.

    """
    if len(legend_values) < max_labels:
        legend_ticks = legend_values
    else:
        spacer = int(math.ceil(len(legend_values)/max_labels))
        legend_ticks = []
        for i in legend_values[::spacer]:
            legend_ticks += [i]
            legend_ticks += ['']*(spacer-1)
    return legend_ticks


def linear_gradient(hexList, nColors):
    """
    Given a list of hexcode values, will return a list of length
    nColors where the colors are linearly interpolated between the
    (r, g, b) tuples that are given.

    Examples
    --------
    >>> linear_gradient([(0, 0, 0), (255, 0, 0), (255, 255, 0)], 100)

    """
    def _scale(start, finish, length, i):
        """
        Return the value correct value of a number that is in between start
        and finish, for use in a loop of length *length*.

        """
        base = 16

        fraction = float(i) / (length - 1)
        raynge = int(finish, base) - int(start, base)
        thex = hex(int(int(start, base) + fraction * raynge)).split('x')[-1]
        if len(thex) != 2:
            thex = '0' + thex
        return thex

    allColors = []
    # Separate (R, G, B) pairs.
    for start, end in zip(hexList[:-1], hexList[1:]):
        # Linearly intepolate between pair of hex ###### values and
        # add to list.
        nInterpolate = 765
        for index in range(nInterpolate):
            r = _scale(start[1:3], end[1:3], nInterpolate, index)
            g = _scale(start[3:5], end[3:5], nInterpolate, index)
            b = _scale(start[5:7], end[5:7], nInterpolate, index)
            allColors.append(''.join(['#', r, g, b]))

    # Pick only nColors colors from the total list.
    result = []
    for counter in range(nColors):
        fraction = float(counter) / (nColors - 1)
        index = int(fraction * (len(allColors) - 1))
        result.append(allColors[index])
    return result


def color_brewer(color_code, n=6):
    """
    Generate a colorbrewer color scheme of length 'len', type 'scheme.
    Live examples can be seen at http://colorbrewer2.org/

    """
    maximum_n = 253
    minimum_n = 3

    # Raise an error if the n requested is greater than the maximum.
    if n > maximum_n:
        raise ValueError('The maximum number of colors in a'
                         ' ColorBrewer sequential color series is 253')
    if n < minimum_n:
        raise ValueError('The minimum number of colors in a'
                         ' ColorBrewer sequential color series is 3')

    if not isinstance(color_code, str):
        raise ValueError('color should be a string, not a {}.'
                         .format(type(color_code)))
    if color_code[-2:] == '_r':
        base_code = color_code[:-2]
        core_color_code = base_code + '_' + str(n).zfill(2)
        color_reverse = True
    else:
        base_code = color_code
        core_color_code = base_code + '_' + str(n).zfill(2)
        color_reverse = False

    with open(os.path.join(rootpath, '_schemes.json')) as f:
        schemes = json.loads(f.read())

    with open(os.path.join(rootpath, '_cnames.json')) as f:
        scheme_info = json.loads(f.read())

    with open(os.path.join(rootpath, 'scheme_base_codes.json')) as f:
        core_schemes = json.loads(f.read())['codes']

    if base_code not in core_schemes:
        raise ValueError(base_code + ' is not a valid ColorBrewer code')

    try:
        schemes[core_color_code]
        explicit_scheme = True
    except KeyError:
        explicit_scheme = False

    # Only if n is greater than the scheme length do we interpolate values.
    if not explicit_scheme:
        # Check to make sure that it is not a qualitative scheme.
        if scheme_info[base_code] == 'Qualitative':
            matching_quals = []
            for key in schemes:
                if base_code + '_' in key:
                    matching_quals.append(int(key.split('_')[1]))

            raise ValueError('Expanded color support is not available'
                             ' for Qualitative schemes; restrict the'
                             ' number of colors for the ' + base_code +
                             ' code to between ' + str(min(matching_quals)) +
                             ' and ' + str(max(matching_quals))
                             )
        else:
            if not color_reverse:
                color_scheme = linear_gradient(schemes.get(core_color_code), n)
            else:
                color_scheme = linear_gradient(schemes.get(core_color_code)[::-1], n)
    else:
        if not color_reverse:
            color_scheme = schemes.get(core_color_code, None)
        else:
            color_scheme = schemes.get(core_color_code, None)[::-1]
    return color_scheme


def split_six(series=None):
    """
    Given a Pandas Series, get a domain of values from zero to the 90% quantile
    rounded to the nearest order-of-magnitude integer. For example, 2100 is
    rounded to 2000, 2790 to 3000.

    Parameters
    ----------
    series: Pandas series, default None

    Returns
    -------
    list

    """
    if pd is None:
        raise ImportError('The Pandas package is required'
                          ' for this functionality')
    if np is None:
        raise ImportError('The NumPy package is required'
                          ' for this functionality')

    def base(x):
        if x > 0:
            base = pow(10, math.floor(math.log10(x)))
            return round(x/base)*base
        else:
            return 0

    quants = [0, 50, 75, 85, 90]
    # Some weirdness in series quantiles a la 0.13.
    arr = series.values
    return [base(np.percentile(arr, x)) for x in quants]


def image_to_url(image, colormap=None, origin='upper'):
    """Infers the type of an image argument and transforms it into a URL.

    Parameters
    ----------
    image: string, file or array-like object
        * If string, it will be written directly in the output file.
        * If file, it's content will be converted as embedded in the
          output file.
        * If array-like, it will be converted to PNG base64 string and
          embedded in the output.
    origin : ['upper' | 'lower'], optional, default 'upper'
        Place the [0, 0] index of the array in the upper left or
        lower left corner of the axes.
    colormap : callable, used only for `mono` image.
        Function of the form [x -> (r,g,b)] or [x -> (r,g,b,a)]
        for transforming a mono image into RGB.
        It must output iterables of length 3 or 4, with values between
        0. and 1.  Hint : you can use colormaps from `matplotlib.cm`.
    """
    if hasattr(image, 'read'):
        # We got an image file.
        if hasattr(image, 'name'):
            # We try to get the image format from the file name.
            fileformat = image.name.lower().split('.')[-1]
        else:
            fileformat = 'png'
        url = 'data:image/{};base64,{}'.format(
            fileformat, base64.b64encode(image.read()).decode('utf-8'))
    elif (not (isinstance(image, str) or
               isinstance(image, bytes))) and hasattr(image, '__iter__'):
        # We got an array-like object.
        png = write_png(image, origin=origin, colormap=colormap)
        url = 'data:image/png;base64,' + base64.b64encode(png).decode('utf-8')
    else:
        # We got an URL.
        url = json.loads(json.dumps(image))

    return url.replace('\n', ' ')


def write_png(data, origin='upper', colormap=None):
    """
    Transform an array of data into a PNG string.
    This can be written to disk using binary I/O, or encoded using base64
    for an inline PNG like this:

    >>> png_str = write_png(array)
    >>> 'data:image/png;base64,'+png_str.encode('base64')

    Inspired from
    http://stackoverflow.com/questions/902761/saving-a-numpy-array-as-an-image

    Parameters
    ----------
    data: numpy array or equivalent list-like object.
         Must be NxM (mono), NxMx3 (RGB) or NxMx4 (RGBA)

    origin : ['upper' | 'lower'], optional, default 'upper'
        Place the [0,0] index of the array in the upper left or lower left
        corner of the axes.

    colormap : callable, used only for `mono` image.
        Function of the form [x -> (r,g,b)] or [x -> (r,g,b,a)]
        for transforming a mono image into RGB.
        It must output iterables of length 3 or 4, with values between
        0. and 1.  Hint: you can use colormaps from `matplotlib.cm`.

    Returns
    -------
    PNG formatted byte string
    """
    if np is None:
        raise ImportError('The NumPy package is required'
                          ' for this functionality')

    if colormap is None:
        def colormap(x):
            return (x, x, x, 1)

    array = np.atleast_3d(data)
    height, width, nblayers = array.shape

    if nblayers not in [1, 3, 4]:
        raise ValueError('Data must be NxM (mono), '
                         'NxMx3 (RGB), or NxMx4 (RGBA)')
    assert array.shape == (height, width, nblayers)

    if nblayers == 1:
        array = np.array(list(map(colormap, array.ravel())))
        nblayers = array.shape[1]
        if nblayers not in [3, 4]:
            raise ValueError('colormap must provide colors of'
                             'length 3 (RGB) or 4 (RGBA)')
        array = array.reshape((height, width, nblayers))
    assert array.shape == (height, width, nblayers)

    if nblayers == 3:
        array = np.concatenate((array, np.ones((height, width, 1))), axis=2)
        nblayers = 4
    assert array.shape == (height, width, nblayers)
    assert nblayers == 4

    # Normalize to uint8 if it isn't already.
    if array.dtype != 'uint8':
        array = array * 255./array.max(axis=(0, 1)).reshape((1, 1, 4))
        array = array.astype('uint8')

    # Eventually flip the image.
    if origin == 'lower':
        array = array[::-1, :, :]

    # Transform the array to bytes.
    raw_data = b''.join([b'\x00' + array[i, :, :].tobytes()
                         for i in range(height)])

    def png_pack(png_tag, data):
        chunk_head = png_tag + data
        return (struct.pack('!I', len(data)) +
                chunk_head +
                struct.pack('!I', 0xFFFFFFFF & zlib.crc32(chunk_head)))

    return b''.join([
        b'\x89PNG\r\n\x1a\n',
        png_pack(b'IHDR', struct.pack('!2I5B', width, height, 8, 6, 0, 0, 0)),
        png_pack(b'IDAT', zlib.compress(raw_data, 9)),
        png_pack(b'IEND', b'')])


def _camelify(out):
    return (''.join(['_' + x.lower() if i < len(out)-1 and x.isupper() and out[i+1].islower()  # noqa
         else x.lower() + '_' if i < len(out)-1 and x.islower() and out[i+1].isupper()  # noqa
         else x.lower() for i, x in enumerate(list(out))])).lstrip('_').replace('__', '_')  # noqa


# def _parse_size(value):
#     try:
#         if isinstance(value, int) or isinstance(value, float):
#             value_type = 'px'
#             value = float(value)
#             assert value > 0
#         else:
#             value_type = '%'
#             value = float(value.strip('%'))
#             assert 0 <= value <= 100
#     except Exception:
#         msg = 'Cannot parse value {!r} as {!r}'.format
#         raise ValueError(msg(value, value_type))
#     return value, value_type


def _locations_mirror(x):
    """Mirrors the points in a list-of-list-of-...-of-list-of-points.
    For example:
    >>> _locations_mirror([[[1, 2], [3, 4]], [5, 6], [7, 8]])
    [[[2, 1], [4, 3]], [6, 5], [8, 7]]

    """
    if hasattr(x, '__iter__'):
        if hasattr(x[0], '__iter__'):
            return list(map(_locations_mirror, x))
        else:
            return list(x[::-1])
    else:
        return x


def _locations_tolist(x):
    """Transforms recursively a list of iterables into a list of list.
    """
    if hasattr(x, '__iter__'):
        return list(map(_locations_tolist, x))
    else:
        return x


def none_min(x, y):
    if x is None:
        return y
    elif y is None:
        return x
    else:
        return min(x, y)


def none_max(x, y):
    if x is None:
        return y
    elif y is None:
        return x
    else:
        return max(x, y)


def iter_points(x):
    """Iterates over a list representing a feature, and returns a list of points,
    whatever the shape of the array (Point, MultiPolyline, etc).
    """
    if isinstance(x, (list, tuple)):
        if len(x):
            if isinstance(x[0], (list, tuple)):
                out = []
                for y in x:
                    out += iter_points(y)
                return out
            else:
                return [x]
        else:
            return []
    else:
        raise ValueError('List/tuple type expected. Got {!r}.'.format(x))

import base64
import io
import json
import math
import os
import struct
import tempfile
import zlib
from contextlib import contextmanager
import copy
import uuid
import collections
from urllib.parse import urlparse, uses_netloc, uses_params, uses_relative

# import numpy as np
try:
    import pandas as pd
except ImportError:
    pd = None


_VALID_URLS = set(uses_relative + uses_netloc + uses_params)
_VALID_URLS.discard('')


def validate_location(location):  # noqa: C901
    """Validate a single lat/lon coordinate pair and convert to a list

    Validate that location:
    * is a sized variable
    * with size 2
    * allows indexing (i.e. has an ordering)
    * where both values are floats (or convertible to float)
    * and both values are not NaN

    Returns
    -------
    list[float, float]

    """
    if isinstance(location, np.ndarray) \
            or (pd is not None and isinstance(location, pd.DataFrame)):
        location = np.squeeze(location).tolist()
    if not hasattr(location, '__len__'):
        raise TypeError('Location should be a sized variable, '
                        'for example a list or a tuple, instead got '
                        '{!r} of type {}.'.format(location, type(location)))
    if len(location) != 2:
        raise ValueError('Expected two (lat, lon) values for location, '
                         'instead got: {!r}.'.format(location))
    try:
        coords = (location[0], location[1])
    except (TypeError, KeyError):
        raise TypeError('Location should support indexing, like a list or '
                        'a tuple does, instead got {!r} of type {}.'
                        .format(location, type(location)))
    for coord in coords:
        try:
            float(coord)
        except (TypeError, ValueError):
            raise ValueError('Location should consist of two numerical values, '
                             'but {!r} of type {} is not convertible to float.'
                             .format(coord, type(coord)))
        if math.isnan(float(coord)):
            raise ValueError('Location values cannot contain NaNs.')
    return [float(x) for x in coords]


def validate_locations(locations):
    """Validate an iterable with multiple lat/lon coordinate pairs.

    Returns
    -------
    list[list[float, float]] or list[list[list[float, float]]]

    """
    locations = if_pandas_df_convert_to_numpy(locations)
    try:
        iter(locations)
    except TypeError:
        raise TypeError('Locations should be an iterable with coordinate pairs,'
                        ' but instead got {!r}.'.format(locations))
    try:
        next(iter(locations))
    except StopIteration:
        raise ValueError('Locations is empty.')
    try:
        float(next(iter(next(iter(next(iter(locations)))))))
    except (TypeError, StopIteration):
        # locations is a list of coordinate pairs
        return [validate_location(coord_pair) for coord_pair in locations]
    else:
        # locations is a list of a list of coordinate pairs, recurse
        return [validate_locations(lst) for lst in locations]


def if_pandas_df_convert_to_numpy(obj):
    """Return a Numpy array from a Pandas dataframe.

    Iterating over a DataFrame has weird side effects, such as the first
    row being the column names. Converting to Numpy is more safe.
    """
    if pd is not None and isinstance(obj, pd.DataFrame):
        return obj.values
    else:
        return obj


def image_to_url(image, colormap=None, origin='upper'):
    """
    Infers the type of an image argument and transforms it into a URL.

    Parameters
    ----------
    image: string, file or array-like object
        * If string, it will be written directly in the output file.
        * If file, it's content will be converted as embedded in the
          output file.
        * If array-like, it will be converted to PNG base64 string and
          embedded in the output.
    origin: ['upper' | 'lower'], optional, default 'upper'
        Place the [0, 0] index of the array in the upper left or
        lower left corner of the axes.
    colormap: callable, used only for `mono` image.
        Function of the form [x -> (r,g,b)] or [x -> (r,g,b,a)]
        for transforming a mono image into RGB.
        It must output iterables of length 3 or 4, with values between
        0. and 1.  You can use colormaps from `matplotlib.cm`.

    """
    if isinstance(image, str) and not _is_url(image):
        fileformat = os.path.splitext(image)[-1][1:]
        with io.open(image, 'rb') as f:
            img = f.read()
        b64encoded = base64.b64encode(img).decode('utf-8')
        url = 'data:image/{};base64,{}'.format(fileformat, b64encoded)
    elif 'ndarray' in image.__class__.__name__:
        img = write_png(image, origin=origin, colormap=colormap)
        b64encoded = base64.b64encode(img).decode('utf-8')
        url = 'data:image/png;base64,{}'.format(b64encoded)
    else:
        # Round-trip to ensure a nice formatted json.
        url = json.loads(json.dumps(image))
    return url.replace('\n', ' ')


def _is_url(url):
    """Check to see if `url` has a valid protocol."""
    try:
        return urlparse(url).scheme in _VALID_URLS
    except Exception:
        return False


def write_png(data, origin='upper', colormap=None):
    """
    Transform an array of data into a PNG string.
    This can be written to disk using binary I/O, or encoded using base64
    for an inline PNG like this:

    >>> png_str = write_png(array)
    >>> "data:image/png;base64,"+png_str.encode('base64')

    Inspired from
    https://stackoverflow.com/questions/902761/saving-a-numpy-array-as-an-image

    Parameters
    ----------
    data: numpy array or equivalent list-like object.
         Must be NxM (mono), NxMx3 (RGB) or NxMx4 (RGBA)

    origin : ['upper' | 'lower'], optional, default 'upper'
        Place the [0,0] index of the array in the upper left or lower left
        corner of the axes.

    colormap : callable, used only for `mono` image.
        Function of the form [x -> (r,g,b)] or [x -> (r,g,b,a)]
        for transforming a mono image into RGB.
        It must output iterables of length 3 or 4, with values between
        0. and 1.  Hint: you can use colormaps from `matplotlib.cm`.

    Returns
    -------
    PNG formatted byte string

    """
    if colormap is None:
        def colormap(x):
            return (x, x, x, 1)

    arr = np.atleast_3d(data)
    height, width, nblayers = arr.shape

    if nblayers not in [1, 3, 4]:
        raise ValueError('Data must be NxM (mono), '
                         'NxMx3 (RGB), or NxMx4 (RGBA)')
    assert arr.shape == (height, width, nblayers)

    if nblayers == 1:
        arr = np.array(list(map(colormap, arr.ravel())))
        nblayers = arr.shape[1]
        if nblayers not in [3, 4]:
            raise ValueError('colormap must provide colors of r'
                             'length 3 (RGB) or 4 (RGBA)')
        arr = arr.reshape((height, width, nblayers))
    assert arr.shape == (height, width, nblayers)

    if nblayers == 3:
        arr = np.concatenate((arr, np.ones((height, width, 1))), axis=2)
        nblayers = 4
    assert arr.shape == (height, width, nblayers)
    assert nblayers == 4

    # Normalize to uint8 if it isn't already.
    if arr.dtype != 'uint8':
        with np.errstate(divide='ignore', invalid='ignore'):
            arr = arr * 255./arr.max(axis=(0, 1)).reshape((1, 1, 4))
            arr[~np.isfinite(arr)] = 0
        arr = arr.astype('uint8')

    # Eventually flip the image.
    if origin == 'lower':
        arr = arr[::-1, :, :]

    # Transform the array to bytes.
    raw_data = b''.join([b'\x00' + arr[i, :, :].tobytes()
                         for i in range(height)])

    def png_pack(png_tag, data):
        chunk_head = png_tag + data
        return (struct.pack('!I', len(data)) +
                chunk_head +
                struct.pack('!I', 0xFFFFFFFF & zlib.crc32(chunk_head)))

    return b''.join([
        b'\x89PNG\r\n\x1a\n',
        png_pack(b'IHDR', struct.pack('!2I5B', width, height, 8, 6, 0, 0, 0)),
        png_pack(b'IDAT', zlib.compress(raw_data, 9)),
        png_pack(b'IEND', b'')])


def mercator_transform(data, lat_bounds, origin='upper', height_out=None):
    """
    Transforms an image computed in (longitude,latitude) coordinates into
    the a Mercator projection image.

    Parameters
    ----------

    data: numpy array or equivalent list-like object.
        Must be NxM (mono), NxMx3 (RGB) or NxMx4 (RGBA)

    lat_bounds : length 2 tuple
        Minimal and maximal value of the latitude of the image.
        Bounds must be between -85.051128779806589 and 85.051128779806589
        otherwise they will be clipped to that values.

    origin : ['upper' | 'lower'], optional, default 'upper'
        Place the [0,0] index of the array in the upper left or lower left
        corner of the axes.

    height_out : int, default None
        The expected height of the output.
        If None, the height of the input is used.

    See https://en.wikipedia.org/wiki/Web_Mercator for more details.

    """
    import numpy as np

    def mercator(x):
        return np.arcsinh(np.tan(x*np.pi/180.))*180./np.pi

    array = np.atleast_3d(data).copy()
    height, width, nblayers = array.shape

    lat_min = max(lat_bounds[0], -85.051128779806589)
    lat_max = min(lat_bounds[1], 85.051128779806589)
    if height_out is None:
        height_out = height

    # Eventually flip the image
    if origin == 'upper':
        array = array[::-1, :, :]

    lats = (lat_min + np.linspace(0.5/height, 1.-0.5/height, height) *
            (lat_max-lat_min))
    latslats = (mercator(lat_min) +
                np.linspace(0.5/height_out, 1.-0.5/height_out, height_out) *
                (mercator(lat_max)-mercator(lat_min)))

    out = np.zeros((height_out, width, nblayers))
    for i in range(width):
        for j in range(nblayers):
            out[:, i, j] = np.interp(latslats, mercator(lats),  array[:, i, j])

    # Eventually flip the image.
    if origin == 'upper':
        out = out[::-1, :, :]
    return out


def none_min(x, y):
    if x is None:
        return y
    elif y is None:
        return x
    else:
        return min(x, y)


def none_max(x, y):
    if x is None:
        return y
    elif y is None:
        return x
    else:
        return max(x, y)


def iter_coords(obj):
    """
    Returns all the coordinate tuples from a geometry or feature.

    """
    if isinstance(obj, (tuple, list)):
        coords = obj
    elif 'features' in obj:
        coords = [geom['geometry']['coordinates'] for geom in obj['features']]
    elif 'geometry' in obj:
        coords = obj['geometry']['coordinates']
    else:
        coords = obj.get('coordinates', obj)
    for coord in coords:
        if isinstance(coord, (float, int)):
            yield tuple(coords)
            break
        else:
            for f in iter_coords(coord):
                yield f


def _locations_mirror(x):
    """
    Mirrors the points in a list-of-list-of-...-of-list-of-points.
    For example:
    >>> _locations_mirror([[[1, 2], [3, 4]], [5, 6], [7, 8]])
    [[[2, 1], [4, 3]], [6, 5], [8, 7]]

    """
    if hasattr(x, '__iter__'):
        if hasattr(x[0], '__iter__'):
            return list(map(_locations_mirror, x))
        else:
            return list(x[::-1])
    else:
        return x


def get_bounds(locations, lonlat=False):
    """
    Computes the bounds of the object in the form
    [[lat_min, lon_min], [lat_max, lon_max]]

    """
    bounds = [[None, None], [None, None]]
    for point in iter_coords(locations):
        bounds = [
            [
                none_min(bounds[0][0], point[0]),
                none_min(bounds[0][1], point[1]),
            ],
            [
                none_max(bounds[1][0], point[0]),
                none_max(bounds[1][1], point[1]),
            ],
        ]
    if lonlat:
        bounds = _locations_mirror(bounds)
    return bounds


def camelize(key):
    """Convert a python_style_variable_name to lowerCamelCase.

    Examples
    --------
    >>> camelize('variable_name')
    'variableName'
    >>> camelize('variableName')
    'variableName'
    """
    return ''.join(x.capitalize() if i > 0 else x
                   for i, x in enumerate(key.split('_')))
def parse_options(**kwargs):
    """Return a dict with lower-camelcase keys and non-None values.."""
    return {camelize(key): value
            for key, value in kwargs.items()
            if value is not None}

# def _parse_size(value):
#     try:
#         if isinstance(value, (int, float)):
#             assert value > 0
#             value = str(value) + 'px'
#         else:
#             float(value.strip('%'))
#             assert 0 <= value <= 100
#             value = str(value) + '%'
#     except Exception:
#         msg = 'Cannot parse value {!r} as {!r}'.format
#         raise ValueError(msg(value))
#     return value


def iter_points(x):
    """Iterates over a list representing a feature, and returns a list of points,
    whatever the shape of the array (Point, MultiPolyline, etc).
    """
    if not isinstance(x, (list, tuple)):
        raise ValueError('List/tuple type expected. Got {!r}.'.format(x))
    if len(x):
        if isinstance(x[0], (list, tuple)):
            out = []
            for y in x:
                out += iter_points(y)
            return out
        else:
            return [x]
    else:
        return []


def compare_rendered(obj1, obj2):
    """
    Return True/False if the normalized rendered version of
    two folium map objects are the equal or not.

    """
    return normalize(obj1) == normalize(obj2)


def normalize(rendered):
    """Return the input string without non-functional spaces or newlines."""
    out = ''.join([line.strip()
                   for line in rendered.splitlines()
                   if line.strip()])
    out = out.replace(', ', ',')
    return out


@contextmanager
def _tmp_html(data):
    """Yields the path of a temporary HTML file containing data."""
    filepath = ''
    try:
        fid, filepath = tempfile.mkstemp(suffix='.html', prefix='folium_')
        os.write(fid, data.encode('utf8'))
        os.close(fid)
        yield filepath
    finally:
        if os.path.isfile(filepath):
            os.remove(filepath)


def deep_copy(item_original):
    """Return a recursive deep-copy of item where each copy has a new ID."""
    item = copy.copy(item_original)
    item._id = uuid.uuid4().hex
    if hasattr(item, '_children') and len(item._children) > 0:
        children_new = collections.OrderedDict()
        for subitem_original in item._children.values():
            subitem = deep_copy(subitem_original)
            subitem._parent = item
            children_new[subitem.get_name()] = subitem
        item._children = children_new
    return item


def get_obj_in_upper_tree(element, cls):
    """Return the first object in the parent tree of class `cls`."""
    if not hasattr(element, '_parent'):
        raise ValueError('The top of the tree was reached without finding a {}'
                         .format(cls))
    parent = element._parent
    if not isinstance(parent, cls):
        return get_obj_in_upper_tree(parent, cls)
    return parent



