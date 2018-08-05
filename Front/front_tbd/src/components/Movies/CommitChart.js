import { Pie, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

let options =  {
  legend: {
    labels: {
      fontColor: 'white',
      fontSize: 18
    }
  },
  cutoutPercentage: 0,
  rotation: -0.5 * Math.PI,
  circumference: 2 * Math.PI * 1,
}

export default {
  extends: Pie,
  mixins: [reactiveProp],
  props: [options],
  mounted () {
    // this.chartData is created in the mixin.
    // If you want to pass options please create a local options object
    this.renderChart(this.chartData, options)
  }
}
