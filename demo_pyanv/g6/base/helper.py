#coding:utf8
import math
import pandas as pd
import numpy as np
# def _parse_size(value):
#     try:
#         if isinstance(value, (int, float)):
#             assert value > 0
#             value = str(value) + 'px'
#         else:
#             value = float(value.strip('%'))
#             assert 0 <= value <= 100
#             value = str(value) + '%'
#     except Exception:
#         msg = 'Cannot parse value {!r} as {!r}'.format
#         raise ValueError(msg(value))
#     return value
def _parse_size(value):
    try:
        if isinstance(value, int) or isinstance(value, float):
            value_type = 'px'
            value = float(value)
            assert value > 0
        else:
            value_type = '%'
            value = float(value.strip('%'))
            assert 0 <= value <= 100
    except Exception:
        msg = 'Cannot parse value {!r} as {!r}'.format
        raise ValueError(msg(value, value_type))
    return value, value_type

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

def _camelify(out):
    return (''.join(['_' + x.lower() if i < len(out)-1 and x.isupper() and out[i+1].islower()  # noqa
         else x.lower() + '_' if i < len(out)-1 and x.islower() and out[i+1].isupper()  # noqa
         else x.lower() for i, x in enumerate(list(out))])).lstrip('_').replace('__', '_')  # noqa

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