# -*- coding: utf-8 -*- 
# Time: 2021-11-18 10:16
# Copyright (c) 2021
# author: Euraxluo
import json
from graph import Graph, BackgroundOptions

g = Graph()
with open("./test_data.json", "r") as f:
    g.data = f.read()

g.updateBackground(background=BackgroundOptions(color='yellow'))
g.updateBackground(background={'color': 'red'})
g.updateBackground(background=False)
# g.updateBackground()
# g.clearBackground()
# g.updateBackground()

# g.updateGrid(grid=GridOptions(color='red', visible=True, size=30, thickness=3.0))
# g.updateGrid(grid={'size': 10})
# g.updateGrid(grid=False)
# g.showGrid()
# g.hideGrid()
# g.showGrid()
# g.setGridSize(50)
# print(g.getGridSize())

# g.enableSnapline()
# g.disableSnapline()
# g.toggleSnapline()
# g.toggleSnapline()
# g.toggleSnapline(False)
# g.updateSnapline(snapline=SnaplineOptions(enabled=True))
# g.updateSnapline(snapline=False)
# g.updateSnapline(snapline={'enabled':True})

# g.toggleScroller()
# g.disablePannable()

print(g.getOpt())
g.save("../test/g.html")
