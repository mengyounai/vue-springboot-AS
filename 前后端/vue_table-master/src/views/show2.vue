<template>
  <div class="home-container">
    <div class="wrap">
<!--    <div>这是测试页面</div>-->
    <div id="myChart" :style="{ width: '550px', height: '550px' }"></div>
<!--    <button @click="click()">数据传输测试Test</button>-->
    </div>
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
    window.setInterval(() => {
      setTimeout(() => {
        this.getdataX()
        this.getdataY()
        this.getdataZ()
        // this.click()
      }, 0)
    }, 1000)
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
        })
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
        // 标题字体设置
        textStyle: {
          fontWeight: 'normal',
          color: '#408829'
        },
        title: {
          text: 'xyz数据',
          color: '#FF0087',
          textStyle: {
            fontWeight: 'normal',
            color: '#fff'
          }
        },
        // 背景颜色
        // backgroundColor: '#408829',
        // borderColor: '#408829',
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
          data: ['X轴数据', 'Line 2', 'Y轴数据', 'Z轴数据', 'Line 5'],
          textStyle: {
            fontWeight: 'bold',
            color: '#fff'
          }
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
            data: ['1st', '2nd', '3rd', '4th', '5th', '6th', '7th', '8th', '9th', '10th']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: 'X轴数据',
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
            name: 'Y轴数据',
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
            name: 'Z轴数据',
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
  /*position: absolute;*/
  /*left: 0;*/
  /*top: 0;*/
  /*width: 100%;*/
  /*height: 100%;*/
  margin: 0 auto;

}
.wrap {
  transform-origin: 0px 0px 0px;
  background: url(../assets/img/bj.jpg) no-repeat;
  background-size: contain;
  background-position: 50% 0;
  background-color: rgb(0, 0, 0);
  min-width: auto;
  width: 100%;
  height: 100%;
  min-height: auto;
  overflow: auto;
}
#myChart{
  position: absolute;
  top: 50%;
  margin-top: -350px;
  left: 50%;
  margin-left: -250px;
  width: 100%;
  height: 100%;
  text-align: center;
}

</style>
