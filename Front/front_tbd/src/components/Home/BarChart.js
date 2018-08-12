import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

// let options =  {
  // legend: {
  //   labels: {
  //     fontColor: 'white',
  //     fontSize: 18
  //   }
  // },
  // reponsive: true,
//   scales: {
//       yAxes: [{
//          gridLines: {
//            color: 'rgba(255, 255, 255, 0.6)'
//          },
//          ticks: {
//            fontSize: 16,
//            fontColor: '#ffffff'
//          }
//       }],
//       xAxes: [{
//         gridLines: {
//           color: 'rgba(255, 255, 255, 0.6)'
//         },
//         ticks: {
//           fontSize: 16,
//           fontColor: '#ffffff'
//         }
//      }]
//    }
// }

var options = {
  scales: {
    yAxes: [{
      gridLines: {
        color: '#ffffff'
      },
      ticks: {
        fontSize: 16,
        fontColor: '#ffffff'
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
