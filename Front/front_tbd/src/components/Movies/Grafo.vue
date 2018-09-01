 <template>
   <v-container fluid grid-list-xs>
        <h2 class="graph-title">Influencia de los usuarios en los distintos géneros de películas.</h2>
        <v-layout row wrap justify-center>
            <v-flex xs12 sm12 md2 lg2 xl2 elevation-5 class="flex">
                <v-flex xs12 class="codigo">
                        <div class="usuarios"><h1>Usuarios</h1></div>
                        <div class="generos"><h1>Géneros</h1></div>
                        <div class="pelicula"><h1>Película</h1></div>
                </v-flex>
            </v-flex>
            <v-flex xs12 sm12 md10 lg10 xl10 elevation-5>
                <div id="grafo">
                    <div id="grafito"></div>
                </div>
            </v-flex>
            <v-flex xs12>
                <v-data-table dark
                    :headers="headers"
                    :items="gen_table"
                    class="elevation-1"
                >
                    <template slot="headerCell" slot-scope="props">
                    <v-tooltip bottom>
                        <span slot="activator">
                        {{ props.header.text }}
                        </span>
                        <span>
                        {{ props.header.text }}
                        </span>
                    </v-tooltip>
                    </template>
                    <template slot="items" slot-scope="props">
                    <td>{{ props.item.name }}</td>
                    <td class="text-xs-center">{{ props.item.influencia }}</td>
                    </template>
                </v-data-table>
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
                url: 'http://167.99.155.164:8080',
                group_id: 2,
                group_id_size: [],
                graph: {
                    "nodes": [{
                        name: "",
                        group: 0
                    }],
                    "links": [{
                        source: 0,
                        target: 0
                    }]
                },
                headers: [
                    {
                        text: 'Géneros',
                        align: 'left',
                        sortable: false,
                        value: 'name'
                    },
                    { text: 'Influencia (Puntos)', value: 'influencia', align: 'center'}
                ],
                gen_table: [],
                desserts: [
                {
                    value: false,
                    name: 'Romance',
                    influencia: 67,
                },
                {
                    value: false,
                    name: 'Terror',
                    influencia: 72,
                },
                {
                    value: false,
                    name: 'Acción',
                    influencia: 62,
                },
                {
                    value: false,
                    name: 'Infantil',
                    influencia: 58,
                },
                ],
                graph_demo: {
                    "nodes": [{
                        name: "",
                        group: 0
                    },
                    {
                        name: "Acción",
                        group: 1
                    },
                    {
                        name: "Romance",
                        group: 1
                    },
                    {
                        name: "Terror",
                        group: 1
                    },
                    {
                        name: "Infantil",
                        group: 1
                    },
                    {
                        name: "LaTercera",
                        group: 2
                    },
                    {
                        name: "RNTV",
                        group: 2
                    },
                    {
                        name: "CarmenG",
                        group: 2
                    },
                    {
                        name: "DylanAckles",
                        group: 2
                    },
                    {
                        name: "Espinof",
                        group: 2
                    },
                    {
                        name: "Milenio.com",
                        group: 2
                    },
                    {
                        name: "RNTV",
                        group: 2
                    },
                    {
                        name: "CineMundo",
                        group: 2
                    },
                    {
                        name: "markozaror",
                        group: 2
                    },
                    {
                        name: "AVATTE",
                        group: 2
                    },
                    {
                        name: "TrendsSantiago",
                        group: 2
                    },
                    {
                        name: "CineMundo",
                        group: 2
                    },
                    {
                        name: "elclubpelicula",
                        group: 2
                    },
                    {
                        name: "SANFICFESTIVAL",
                        group: 2
                    },
                    {
                        name: "cinemachile",
                        group: 2
                    },
                    {
                        name: "CinemarkChile",
                        group: 2
                    }
                    ],
                    "links": [{
                        source: 0,
                        target: 0
                    },
                    {
                        source: 1,
                        target: 0
                    },
                    {
                        source: 2,
                        target: 0
                    },
                    {
                        source: 3,
                        target: 0
                    },
                    {
                        source: 4,
                        target: 0
                    },
                    {
                        source: 5,
                        target: 1
                    },
                    {
                        source: 6,
                        target: 1
                    },
                    {
                        source: 7,
                        target: 1
                    },
                    {
                        source: 8,
                        target: 1
                    },
                    {
                        source: 9,
                        target: 2
                    },
                    {
                        source: 9,
                        target: 2
                    },
                    {
                        source: 10,
                        target: 2
                    },
                    {
                        source: 11,
                        target: 2
                    },
                    {
                        source: 12,
                        target: 2
                    },
                    {
                        source: 13,
                        target: 3
                    },
                    {
                        source: 14,
                        target: 3
                    },
                    {
                        source: 15,
                        target: 3
                    },
                    {
                        source: 16,
                        target: 3
                    },
                    {
                        source: 17,
                        target: 4
                    },
                    {
                        source: 18,
                        target: 4
                    },
                    {
                        source: 19,
                        target: 4
                    },
                    {
                        source: 20,
                        target: 4
                    }
                    ]
                }
            }
        },
        methods:{
            loadGraph(input, inputSize){
                var width = 1180
                var height = 1000
                
                var body = d3.select("#grafo");
                var svg = body.select("#grafito").append("svg")
                            .attr("width", width)
                            .attr("height", height)
                
                var force = d3.forceSimulation()
                              .force("charge", d3.forceManyBody().strength(-900).distanceMin(150).distanceMax(900)) 
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

                var size = function (group, size) {
                    console.log("GROUP IS: "+group)
                    if(group == 0) {
                        return 10
                    } else if (group == 1) {
                        return 15
                    } else {
                        for(let i = 0; i < size.length; i ++){
                            if(size[i].group === group)
                                return size[i].val
                        }
                    }
                        
                        // for(let i = 0; i < this.data.generos.lenght;i++){
                        //     let influencia = this.data.generos[i].valorizacionneofourj

                        // }
                    
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
                        return size(d.group, inputSize)
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
                axios.get(this.url+'/neofourjay-0.0.1-SNAPSHOT/init/getStatics')
                     .then((response) => {
                         this.data = response.data;
                         let count = 0;

                         for(let i = 0; i < this.data.generos.length; i++)
                         {
                            this.graph.nodes.push({
                                name: this.data.generos[i].nombre,
                                group: this.group_id
                            });

                            this.graph.links.push({
                                source: i+1,
                                target: 0
                            });

                            this.group_id_size.push({
                                group: this.group_id,
                                val: (40 * (this.data.generos[i].valorizacionneofourj/25) + 15)
                            });

                            this.group_id++
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

                        // for(let k = 1; k < this.data.generos.length; k++){
                        //     let val = this.data.generos[k].valorizacionneofourj;
                        //     console.log("el genero: " + this.data.generos[k].nombre + " con una val: "+val);
                        //     let total = 40 * (val / 25) + 10;
                        //     this.group_id_size.push(total);
                        // }



                     })
                     .catch((err) => console.error(err))
                     .then(() => {
                         console.log("GROUP_ID_SIZE: " + this.group_id_size);
                         this.loadGraph(this.graph, this.group_id_size);
                     })
                     .catch((err) => console.log(err))
                     .then(() => {
                         this.loadDatatoTable();
                     })
                     .catch((err) => console.error(err));

            },
            loadDatatoTable(){
                for(let i = 0; i < this.data.generos.length; i++){
                    this.gen_table.push({
                        name: this.data.generos[i].nombre,
                        influencia: this.data.generos[i].valorizacionneofourj
                    });
                }
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
        stroke-width: 0.2%;
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
        margin: 1% 1%;
    }

    .codigo {
        /* margin: 25% 2%; */
    }
    
</style>