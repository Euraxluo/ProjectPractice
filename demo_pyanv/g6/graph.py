# coding:utf8
import folium
import warnings
import json
import os
from jinja2 import Template, FileSystemLoader, Environment
from typing import Any, Optional, Sequence, Tuple, Union, AnyStr

Numeric = Union[int, float]
from base.base import MacroElement, Figure, Element, JavascriptLink, CssLink
from base.base_object import BasicOpts, BackgroundOptions, GridOptions
from base.helper import _parse_size, parse_options, validate_location

ENV = Environment(
    loader=FileSystemLoader(
        os.path.join(
            os.path.abspath(os.path.dirname(__file__)), "templates"
        )
    ),
)

_default_js = [
    ('g6', 'https://cdn.jsdelivr.net/npm/@antv/g6/dist/g6.js'),

]

_default_css = [
    ('leaflet_css',
     'https://cdn.jsdelivr.net/npm/leaflet@1.5.1/dist/leaflet.css'),
]


class Graph(MacroElement, BasicOpts):
    _template = Template(u"""
    {% macro header(this, kwargs) %}
        <!--<style>
            #{{ this.get_name() }} {
                width: {{this.width}};
                height: {{this.height}};
            }
        </style>-->
    {% endmacro %}
    
    {% macro html(this, kwargs) %}
        <div class="{{ this.get_name() }}" id={{ this.get_name()|tojson }} ></div>
    {% endmacro %}
        
    {% macro script(this, kwargs) %}
        const {{ this.get_name() }} = new G6.Graph({
                container: document.getElementById({{ this.get_name()|tojson }}),
                {%- for key, value in this.options.items() %}
                {{ key }}: {{ value|tojson }},
                {%- endfor %}
                }
        );
        {{this.get_name()}}.read({{ this.data() }});
    {% endmacro %}
    """)

    def drawBackground(
            self, background: Union[BackgroundOptions, dict, None] = BackgroundOptions()
    ):
        if isinstance(background, dict):
            self._background.update(**BackgroundOptions(**background).get())
        elif isinstance(background, BackgroundOptions):
            self._background.update(**background.get())
        self.updateBackground()

    def updateBackground(self):
        self.options['background'] = self._background.get()

    def clearBackground(self):
        self._background = None

    def drawGrid(self, grid: Union[GridOptions, dict, None] = GridOptions()):
        if isinstance(grid, dict):
            self._grid.update(**GridOptions(**grid).get())
        elif isinstance(grid, GridOptions):
            self._grid.update(**grid.get())
        self.updateGrid()

    def updateGrid(self):
        self.options['grid'] = self._grid.get()

    def getGridSize(self) -> Optional[Numeric]:
        return self._grid.get('size')

    def setGridSize(self, gridSize: Optional[Numeric] = 10):
        self._grid.update(size=gridSize)

    def showGrid(self):
        self._grid.update(visible=True)

    def hideGrid(self):
        self._grid.update(visible=False)

    def clearBackground(self):
        self._grid = None

    def data(self):
        data = """
        {
            "nodes": [
                {
                    "id": "node1",
                    "x": 40,
                    "y": 40,
                    "width": 100,
                    "height": 40,
                    "attrs": {
                        "body": {
                            "fill": "#2ECC71",
                            "stroke": "#000",
                            "strokeDasharray": "10,2"
                        },
                        "label": {
                            "text": "Hello",
                            "fill": "#333",
                            "fontSize": 13
                        }
                    }
                },
                {
                    "id": "node2",
                    "x": 180,
                    "y": 240,
                    "width": 100,
                    "height": 40,
                    "attrs": {
                        "body": {
                            "fill": "#F39C12",
                            "stroke": "#000",
                            "rx": 16,
                            "ry": 16
                        },
                        "label": {
                            "text": "World",
                            "fill": "#333",
                            "fontSize": 18,
                            "fontWeight": "bold",
                            "fontVariant": "small-caps"
                        }
                    }
                }
            ],
            "edges": [
                {
                    "source": "node1",
                    "target": "node2",
                    "shape": "edge",
                    "attrs": {
                        "line": {
                            "stroke": "orange"
                        }
                    }
                }
            ]
        }
        """
        return data

    def __init__(
            self,
            container: Optional[str] = "container",
            width: Numeric = 800,
            height: Numeric = 800,
            fitView: bool = False,
            fitViewPadding: Union[Numeric, Sequence] = 0,
            fitCenter: bool = False,
            linkCenter: bool = False,
            groupByTypes: bool = True,
            autoPaint: bool = True,
            minZoom: Numeric = 0.2,
            maxZoom: Numeric = 10,
            renderer: Union['canvas', 'svg'] = 'canvas',
            enabledStack: bool = False,
            maxStep: Numeric = 10,
            **kwargs
    ):
        super(Graph, self).__init__()
        self._name = 'Graph'
        self._env = ENV
        Figure().add_child(self)
        self.width = _parse_size(width)[0]
        self.height = _parse_size(height)[0]
        self.container = container
        self.options = parse_options(
            width=self.width,
            height=self.height,
            fitView=fitView,
            fitViewPadding=fitViewPadding,
            fitCenter=fitCenter,
            linkCenter=linkCenter,
            groupByTypes=groupByTypes,
            autoPaint=autoPaint,
            minZoom=minZoom,
            maxZoom=maxZoom,
            renderer=renderer,
            enabledStack=enabledStack,
            maxStep=maxStep,
            **kwargs
        )

    def _repr_html_(self, **kwargs):
        """Displays the HTML Map in a Jupyter notebook."""
        if self._parent is None:
            self.add_to(Figure())
            out = self._parent._repr_html_(**kwargs)
            self._parent = None
        else:
            out = self._parent._repr_html_(**kwargs)
        return out

    #
    # def _to_png(self, delay=3):
    #     """Export the HTML to byte representation of a PNG image.
    #
    #     Uses selenium to render the HTML and record a PNG. You may need to
    #     adjust the `delay` time keyword argument if maps render without data or tiles.
    #
    #     Examples
    #     --------
    #     >>> m._to_png()
    #     >>> m._to_png(time=10)  # Wait 10 seconds between render and snapshot.
    #
    #     """
    #     if self._png_image is None:
    #         # from selenium import webdriver
    #         #
    #         # options = webdriver.firefox.options.Options()
    #         # options.add_argument('--headless')
    #         # driver = webdriver.Firefox(options=options)
    #         #
    #         # html = self.get_root().render()
    #         # with _tmp_html(html) as fname:
    #         #     We need the tempfile to avoid JS security issues.
    #         # driver.get('file:///{path}'.format(path=fname))
    #         # driver.maximize_window()
    #         # time.sleep(delay)
    #         # png = driver.get_screenshot_as_png()
    #         # driver.quit()
    #         # self._png_image = png
    #         pass
    #     return self._png_image
    #
    # def _repr_png_(self):
    #     """Displays the PNG Map in a Jupyter notebook."""
    #     # The notebook calls all _repr_*_ by default.
    #     # We don't want that here b/c this one is quite slow.
    #     if not self.png_enabled:
    #         return None
    #     return self._to_png()
    #
    def render(self, **kwargs):
        """Renders the HTML representation of the element."""
        figure = self.get_root()
        assert isinstance(figure, Figure), ('You cannot render this Element '
                                            'if it is not in a Figure.')
        # Import Javascripts
        for name, url in _default_js:
            figure.header.add_child(JavascriptLink(url), name=name)
        # Import Css
        for name, url in _default_css:
            figure.header.add_child(CssLink(url), name=name)

        figure.header.add_child(Element(
            '<style>html, body {'
            'width: 100%;'
            'height: 100%;'
            'margin: 0;'
            'padding: 0;'
            '}'
            '</style>'), name='css_style')

        super(Graph, self).render(**kwargs)

    #
    # def fit_bounds(self, bounds, padding_top_left=None,
    #                padding_bottom_right=None, padding=None, max_zoom=None):
    #     """Fit the map to contain a bounding box with the
    #     maximum zoom level possible.
    #
    #     Parameters
    #     ----------
    #     bounds: list of (latitude, longitude) points
    #         Bounding box specified as two points [southwest, northeast]
    #     padding_top_left: (x, y) point, default None
    #         Padding in the top left corner. Useful if some elements in
    #         the corner, such as controls, might obscure objects you're zooming
    #         to.
    #     padding_bottom_right: (x, y) point, default None
    #         Padding in the bottom right corner.
    #     padding: (x, y) point, default None
    #         Equivalent to setting both top left and bottom right padding to
    #         the same value.
    #     max_zoom: int, default None
    #         Maximum zoom to be used.
    #
    #     Examples
    #     --------
    #     >>> m.fit_bounds([[52.193636, -2.221575], [52.636878, -1.139759]])
    #
    #     """
    #     pass
    #     # self.add_child(FitBounds(bounds,
    #     #                          padding_top_left=padding_top_left,
    #     #                          padding_bottom_right=padding_bottom_right,
    #     #                          padding=padding,
    #     #                          max_zoom=max_zoom,
    #     #                          )
    #     #                )
    #
    # def choropleth(self, *args, **kwargs):
    #     """Call the Choropleth class with the same arguments.
    #
    #     This method may be deleted after a year from now (Nov 2018).
    #     """
    #     warnings.warn(
    #         'The choropleth  method has been deprecated. Instead use the new '
    #         'Choropleth class, which has the same arguments. See the example '
    #         'notebook \'GeoJSON_and_choropleth\' for how to do this.',
    #         FutureWarning
    #     )
    #     from folium.features import Choropleth
    #     self.add_child(Choropleth(*args, **kwargs))
    #
    # def keep_in_front(self, *args):
    #     """Pass one or multiples object that must stay in front.
    #
    #     The ordering matters, the last one is put on top.
    #
    #     Parameters
    #     ----------
    #     *args :
    #         Variable length argument list. Any folium object that counts as an
    #         overlay. For example FeatureGroup or a vector object such as Marker.
    #     """
    #     for obj in args:
    #         self.objects_to_stay_in_front.append(obj)


if __name__ == '__main__':
    # g = Graph()
    # print(g.get())
    # g.drawBackground(background=BackgroundOptions(color='yellow'))
    # print(g.get())
    #
    # g.drawGrid(grid=GridOptions(color='yellow',visible=False))
    # print(g.get())
    #
    # g.drawGrid({'color':'red','visible':True})
    # print(g.get())
    #
    # g.hideGrid()
    # print(g.get())
    #
    # g.showGrid()
    # print(g.get())
    #
    # g.setGridSize(gridSize=11)
    # print(g.get())
    # print(g.getGridSize())

    g = Graph()
    g.save("../test/g.html")
