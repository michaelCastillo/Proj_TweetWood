import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

let options =  {
  legend: {
    labels: {
      fontColor: 'white',
      fontSize: 18
    }
  },
  scales: {
      yAxes: [{
         gridLines: {
           color: 'rgba(255, 255, 255, 0.6)'
         },
         ticks: {
           fontSize: 16,
           fontColor: 'white'
         }
      }],
      xAxes: [{
        gridLines: {
          color: 'rgba(255, 255, 255, 0.6)'
        },
        ticks: {
          fontSize: 16,
          fontColor: 'white'
        }
     }]
   }
}

export default {
  extends: Bar,
  mixins: [reactiveProp],
  props: [options],
  mounted () {
    this.renderChart(this.chartData, options)
  }
}
