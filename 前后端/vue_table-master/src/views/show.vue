<template>
  <div class="home-container">
    <div>这是跳转页面</div>
    <div id="myChart" :style="{ width: '500px', height: '500px' }"></div>
    <button @click="click()">数据传输测试</button>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import * as echarts from 'echarts'
import axios from 'axios'
export default {
  name: 'eCharts',
  data() {
    return {
      xdata: [5, 20, 36, 10, 10, 20],
      ydata: [5, 20, 36, 10, 10, 20],
      zdata: [5, 20, 36, 10, 10, 20]
    }
  },
  created() {

  },
  mounted() {
    // 模板挂载完成后调用
    this.getdataX()
    this.getdataY()
    this.getdataZ()
    // this.drawEcharts()
  },
  methods: {
    click() {
      axios.get('http://localhost:8080/api/test')
        .then((res) => {
          console.log(res)
          this.xdata = res.data
        })
      console.log('点击')
    },
    getdataX() {
      axios.get('http://localhost:8080/api/getDistanceX')
        .then((res) => {
          console.log(res)
          this.xdata = res.data
        })
    },
    getdataY() {
      axios.get('http://localhost:8080/api/getDistanceY')
        .then((res) => {
          console.log(res)
          this.ydata = res.data
          this.drawEcharts()
        })
    },
    getdataZ() {
      axios.get('http://localhost:8080/api/getDistanceZ')
        .then((res) => {
          console.log(res)
          this.zdata = res.data
        })
    },
    async  drawEcharts() {
      console.log(this.xdata)
      console.log(this.ydata)
      const myChart = echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.setOption({
        color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00'],
        title: {
          text: 'xyz数据'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['Line 1', 'Line 2', 'Line 3', 'Line 4', 'Line 5']
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: 'Line 1',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgb(128, 255, 165)'
                },
                {
                  offset: 1,
                  color: 'rgb(1, 191, 236)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.xdata
          },
          {
            name: 'Line 3',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgb(55, 162, 255)'
                },
                {
                  offset: 1,
                  color: 'rgb(116, 21, 219)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.ydata
          },
          {
            name: 'Line 4',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 0
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgb(255, 0, 135)'
                },
                {
                  offset: 1,
                  color: 'rgb(135, 0, 157)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.zdata
          }
        ]
      })
    }
  }
}
</script>
<!--<style scoped>-->

<!--</style>-->
<style>
.home-container {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;

}

</style>
