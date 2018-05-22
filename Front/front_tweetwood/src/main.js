import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Home from './Home.vue'
import Film from './Film.vue'
import Graph from './Graph.vue'

Vue.use(VueRouter);

const routes=[
  {path:'/', component: Home},
  {path:'/film/:id', name: 'film', component: Film},
  {path:'/graph', component: Graph}
];

const router=new VueRouter({
  routes,
  mode: 'history'
});

Vue.component('film', Film);
Vue.component('graph', Graph);

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
