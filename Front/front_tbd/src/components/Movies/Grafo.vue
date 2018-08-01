 <template>
 <div>
    <h1>que wea el grafo hermano?</h1>
    <div id="grafo">
        <div id="grafito"></div>
    </div>
 </div>
 </template>
 
 <script>
    import * as d3 from 'd3';

    export default {
        name: 'grafo',
        data(){
            return {
                data: {
                    "nodes": [{
                        "name": "Juan",
                        "group": 1
                    }, {
                        "name": "Juanito",
                        "group": 1
                    }, {
                        "name": "Roberto",
                        "group": 1
                    }, {
                        "name": "Carlos",
                        "group": 1
                    }, {
                        "name": "Julio",
                        "group": 1
                    }, {
                        "name": "Jotita",
                        "group": 2
                    }, {
                        "name": "Oscar",
                        "group": 2
                    }, {
                        "name": "Pablillo",
                        "group": 2
                    }, {
                        "name": "lalo",
                        "group": 3
                    }, {
                        "name": "javo",
                        "group": 3
                    }, {
                        "name": "nico",
                        "group": 3
                    }],
                    "links": [{
                        "source": 0,
                        "target": 1
                    },{
                        "source": 0,
                        "target": 2
                    },{
                        "source": 0,
                        "target": 3
                    },{
                        "source": 0,
                        "target": 4
                    },{
                        "source": 1,
                        "target": 2
                    },{
                        "source": 1,
                        "target": 3
                    },{
                        "source": 1,
                        "target": 4
                    },{
                        "source": 2,
                        "target": 3
                    },{
                        "source": 2,
                        "target": 4
                    },{
                        "source": 0,
                        "target": 5
                    },{
                        "source": 5,
                        "target": 6
                    },{
                        "source": 5,
                        "target": 7
                    },{
                        "source": 0,
                        "target": 8
                    },{
                        "source": 0,
                        "target": 9
                    },{
                        "source": 0,
                        "target": 10
                    },{
                        "source": 8,
                        "target": 7
                    }]
                }
            }
        },
        methods:{
            loadGraph(input){
                var width = 960
                var height = 500
                
                var body = d3.select("#grafo");
                var svg = body.select("#grafito").append("svg")
                            .attr("width", width)
                            .attr("height", height)
                
                var force = d3.forceSimulation()
                              .force("charge", d3.forceManyBody().strength(-700).distanceMin(100).distanceMax(1000)) 
                              .force("link", d3.forceLink().id(function(d) { return d.index })) 
                              .force("center", d3.forceCenter(width / 2, height / 2))
                              .force("y", d3.forceY(0.001))
                              .force("x", d3.forceX(0.001))
                
                var color = function (group) {
                    if (group == 1) {
                        return "#aaa"
                    } else if (group == 2) {
                        return "#fbc280"
                    } else {
                        return "#405275"
                    }
                }

                function dragstarted(d) {
                    if (!d3.event.active) force.alphaTarget(0.5).restart();
                    d.fx = d.x;
                    d.fy = d.y;
                }
                
                function dragged(d) {
                    d.fx = d3.event.x;
                    d.fy = d3.event.y;
                }
                
                function dragended(d) {
                    if (!d3.event.active) force.alphaTarget(0.5);
                    d.fx = null;
                    d.fy = null;
                }
                
                force.nodes(input.nodes).force("link").links(input.links)

                var link = svg.selectAll(".link")
                              .data(input.links)
                              .enter()
                              .append("line")
                              .attr("class", "link");

                var node = svg.selectAll(".node")
                              .data(input.nodes)
                              .enter().append("g")
                              .attr("class", "node")
                              .call(d3.drag()
                              .on("start", dragstarted)
                              .on("drag", dragged)
                              .on("end", dragended));  

                node.append('circle')
                    .attr('r', 13)
                    .attr('fill', function (d) {
                        return color(d.group);
                    });

                node.append("text")
                    .attr("dx", -18)
                    .attr("dy", 8)
                    .style("font-family", "overwatch")
                    .style("font-size", "18px")

                    .text(function (d) {
                        return d.name
                    });

                force.on("tick", function () {
                    link.attr("x1", function (d) {
                        return d.source.x;
                    }).attr("y1", function (d) {
                        return d.source.y;
                    }).attr("x2", function (d) {
                        return d.target.x;
                    }).attr("y2", function (d) {
                        return d.target.y;
                    });
                    
                    node.attr("transform", function (d) {
                        return "translate(" + d.x + "," + d.y + ")";
                    });
                });
            }   
        },
        mounted(){
            this.loadGraph(this.data);
        }
    }
 </script>
 
<style>
    .link {
        stroke: #ccc;
    }

    .node text {
        pointer-events: none;
        font: 10px sans-serif;
    }

    /* @font-face {
        font-family: 'overwatch';
        src: url('fonts/koverwatch.woff2');
    } */
</style>