 <template>
   <v-container fluid grid-list-xs>
        <v-layout row wrap justify-center>
            <v-flex xs12 sm12 md2 lg2 xl2 elevation-5 class="flex">
                <h2 class="graph-title">Influencia de los usuarios en los distintos géneros de películas.</h2>
                
                <v-flex xs12 class="codigo">
                        <div class="usuarios"><h1>Usuarios</h1></div>
                        <div class="generos"><h1>Géneros</h1></div>
                        <div class="pelicula"><h1>Película</h1></div>
                </v-flex>
            </v-flex>
            <v-flex xs12 sm12 md9 lg9 xl9 elevation-5>
                <div id="grafo">
                    <div id="grafito"></div>
                </div>
            </v-flex>        
        </v-layout>

   </v-container>
 </template>
 
 <script>
    import * as d3 from 'd3';
    import axios from 'axios';

    export default {
        name: 'grafo',
        data(){
            return {
                data: null,
                graph: {
                    "nodes": [{
                        name: "",
                        group: 0
                    }],
                    "links": [{
                        source: 0,
                        target: 0
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
                              .force("charge", d3.forceManyBody().strength(-700).distanceMin(150).distanceMax(1000)) 
                              .force("link", d3.forceLink().id(function(d) { return d.index })) 
                              .force("center", d3.forceCenter(width / 2, height / 2))
                              .force("y", d3.forceY(0.001))
                              .force("x", d3.forceX(0.001))
                
                var color = function (group) {
                    if (group == 0) {
                        return "#970c0c"
                    } else if (group == 1) {
                        return "#17cde6"
                    } else {
                        return "#ce7909"
                    }
                }

                var size = function (group) {
                    if(group == 0) {
                        return 15
                    } else if (group == 1) {
                        return 25
                    } else {
                        return 30
                        
                        // for(let i = 0; i < this.data.generos.lenght;i++){
                        //     let influencia = this.data.generos[i].valorizacionneofourj

                        // }
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
                              .on("drag", dragged));
                            //   .on("end", dragended));  

                node.append('circle')
                    .attr('r', function (d) {
                        return size(d.group)
                    })
                    .attr('fill', function (d) {
                        return color(d.group);
                    });

                node.append("text")
                    .attr("dx", -18)
                    .attr("dy", 8)
                    .style("font-family", "roboto")
                    .style("font-size", "14px")

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
                })
            },
            getData(){
                axios.get('http://206.189.224.139:8080/neofourjay-0.0.1-SNAPSHOT/init/getStatics')
                     .then((response) => {
                         this.data = response.data;
                         let count = 0;
                         let group_id = 2;
                         let rom = "Romance";

                         for(let i = 0; i < this.data.generos.length; i++)
                         {

                            if(this.data.generos[i].nombre !== "Inception_"){
                                this.graph.nodes.push({
                                    name: this.data.generos[i].nombre,
                                    group: group_id
                                });
    
                                this.graph.links.push({
                                    source: i+1,
                                    target: 0
                                });
                            }
                            else {
                                this.graph.nodes.push({
                                    name: rom,
                                    group: group_id
                                });
    
                                this.graph.links.push({
                                    source: i+1,
                                    target: 0
                                });
                            }

                            group_id++
                            count++
                         }
                        
                        for(let i = 0; i < this.data.generos.length; i++){
                            for(let j = 0; j < this.data.generos[i].users.length; j++){
                                this.graph.nodes.push({
                                    name: this.data.generos[i].users[j].user,
                                    group: 1
                                })

                                this.graph.links.push({
                                    source: count+1,
                                    target: i+1
                                })

                                count++
                            }
                        }   
                        
                     })
                     .catch((err) => console.error(err))
                     .then(() => {
                         this.loadGraph(this.graph);
                     }).catch((err) => console.log(err));
            }
        },
        beforeMounted(){
            },
        mounted(){
            this.getData();
            // this.loadGraph(this.graph);
            // console.log(this.graph);
        }
    }
 </script>
 
<style>
    .graph-title{
        color: white;
        text-align: center;
        font-size: 25px;
        /* text-justify:; */
    }

    .link {
        stroke: #b10505;
        stroke-width: 0.5%;
    }

    .node text {
        pointer-events: none;
        font-size: 10px;
        font-family: "roboto";
    }

    /* @font-face {
        font-family: 'overwatch';
        src: url('fonts/koverwatch.woff2');
    } */

    #grafito{
        background-color: #d1d1d1;
        /* background-color: #303030; */
        margin: 0% 0%;
    }

    .usuarios {
        margin: 0 auto;
        height: 50px;
        width: 90%;
        background-color: #17cde6;
        text-align: center;
        justify-content: initial;
        color: white;
    }

    .generos {
        margin: 10px auto;
        height: 50px;
        width: 90%;
        background-color: #ce7909;
        text-align: center;
        justify-content: initial;
        color: white;
    }

    .pelicula {
        margin: 0 auto;
        height: 50px;
        width: 90%;
        background-color: #970c0c;
        text-align: center;
        justify-content: initial;
        color: white;
    }

    .flex {
        margin: 2% 2%;
    }

    .codigo {
        margin: 25% 2%;
    }
    
</style>