<template>
  <div :class="className" :style="{ height, width }" ref="chartRef"/>
</template>

<script>
import * as echarts from 'echarts'
import 'echarts/theme/macarons'

import {generateRandomColor} from '@/utils/ruoyi.js'

export default {
  name: 'PieTotalRateCharts',

  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '100%'
    },
    chartTitle: {
      type: String,
      default: '问题分类'
    },
    // 传入的图表数据
    chartData: {
      type: Array,
      default: () => [
        {name: '一级问题', value: 100},
        {name: '二级问题', value: 20},
        {name: '三级问题', value: 30}
      ]
    },
    // 总数
    total: {
      type: Number,
      default: 2000
    },
    // 中心总数文本的名称
    totalName: {
      type: String,
      default: '总数'
    },
    //比例
    rateValue: {
      type: Number,
      default: 1000
    },
    //比例符号
    rateSymbol: {
      type: String,
      default: '‰'
    },
    defaultColor: {
      type: Array,
      default: () => [
        '#5B8FF9', '#5AD8A6', '#5D7092', '#F6BD16', '#E86A92',
        '#7262FD', '#269A29', '#8E36BE', '#41A7E2', '#7747A3',
        '#FF7F50', '#FFDAB9', '#ADFF2F', '#00CED1', '#9370DB',
        '#3CB371', '#FF69B4', '#FFB6C1', '#DA70D6', '#98FB98',
        '#FF6B6B', '#4ECDC4', '#45B7D1', '#96CEB4', '#FFEAA7',
        '#DDA0DD', '#98D8C8', '#F7DC6F', '#BB8FCE', '#85C1E9'
      ]
    }
  },

  data() {
    return {
      chart: null // ECharts 实例
    }
  },

  watch: {
    // 深度侦听 chartData 的变化
    chartData: {
      deep: true,
      handler(newData) {
        this.initChart(newData)
      }
    },
    // 侦听 total 的变化
    total() {
      this.initChart(this.chartData)
    },
    totalName() {
      this.initChart(this.chartData)
    }
  },

  mounted() {
    this.$nextTick(() => {
      this.initChart(this.chartData)
      window.addEventListener('resize', this.handleResize)
    })
  },

  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
    window.removeEventListener('resize', this.handleResize)
  },

  methods: {
    /**
     * 初始化图表
     */
    initChart(data) {
      if (!data || !data.length) {
        return
      }
      //过滤掉值为0的项
      data = data.filter(item => Number(item.value) > 0)
      // 销毁已有实例
      if (this.chart) {
        this.chart.dispose()
        this.chart = null
      }

      const totalValue = this.total
      const rateValue = this.rateValue
      const rateSymbol = this.rateSymbol
      const totalNameValue = this.totalName
      const chartTitleValue = this.chartTitle
      const defaultColorValue = this.defaultColor

      const coloredData = data.map(item => ({
        ...item,
        itemStyle: {
          color: generateRandomColor(defaultColorValue)
        }
      }))

      this.chart = echarts.init(this.$refs.chartRef, 'macarons')

      const option = {
        title: {
          text: this.chartTitle,
          top: 20,
          left: 'center',
          textStyle: {
            color: '#C5E5F9',
            fontSize: 18
          }
        },
        color: defaultColorValue,

        // legend (图例)
        legend: {
          orient: 'horizontal',
          bottom: '5%',
          data: data.map(item => item.name),
          textStyle: {
            color: '#fff',
            fontSize: 14
          },
          icon: 'circle',
          itemGap: 20
        },

        // tooltip: 使用 totalValue 计算千分比 (‰)
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            // 使用外部捕获的 totalValue
            const permillage = (params.value / totalValue * rateValue).toFixed(2)
            return `${params.name}: ${params.value} (${permillage}${rateSymbol})`
          }
        },

        series: [
          {
            name: chartTitleValue,
            type: 'pie',
            center: ['50%', '45%'],
            radius: ['45%', '55%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10
            },
            label: {
              show: true,
              position: 'outside',
              color: '#fff',
              fontSize: 14,
              // 使用自定义函数格式化，计算千分比
              formatter: function (params) {
                // 使用外部捕获的 totalValue
                const permillage = (params.value / totalValue * rateValue).toFixed(2)
                return `${params.name}\n${params.value} (${permillage}${rateSymbol})`
              }
            },
            labelLine: {
              show: true,
              length: 10,
              length2: 10,
              lineStyle: {
                color: '#fff'
              }
            },
            data: coloredData
          },
          // 饼图中心的文本，显示自定义总数
          {
            name: '中心总数',
            type: 'pie',
            center: ['50%', '45%'],
            radius: ['0%', '39%'],
            hoverAnimation: false,
            silent: true,
            itemStyle: {
              color: 'transparent'
            },
            label: {
              show: true,
              position: 'center',
              color: '#fff',
              fontSize: 18,
              // 使用外部捕获的 totalNameValue 和 totalValue
              formatter() {
                return `${totalNameValue}\n\n${totalValue}`
              }
            },
            data: [{value: 1, name: 'Total Placeholder'}]
          }
        ]
      }
      this.chart.setOption(option)
    },

    /**
     * 处理窗口大小变化，重绘图表
     */
    handleResize() {
      if (this.chart) {
        this.chart.resize()
      }
    }
  }
}
</script>

<style scoped>
.chart {
  overflow: hidden;
}
</style>
