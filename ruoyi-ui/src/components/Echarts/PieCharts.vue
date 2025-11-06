<template>
  <div :class="className" :style="{ height, width }" ref="chartRef"/>
</template>

<script>
// 1. 引入 ECharts 核心和主题
import * as echarts from 'echarts'
import 'echarts/theme/macarons' // 引入 'macarons' 主题

import { generateRandomColor } from '@/utils/ruoyi.js'

export default {
  name: 'PieChart',

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
      default: '饼图'
    },
    chartData: {
      type: Array,
      default: () => [
        { name: 'YY', value: 100 },
        { name: 'XC', value: 100 }
      ]
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
        // 当数据变化时，重新初始化图表
        this.initChart(newData)
      }
    }
  },

  mounted() {
    // 使用 $nextTick 确保 DOM 已经渲染
    this.$nextTick(() => {
      this.initChart(this.chartData) // 使用 props 数据初始化
      window.addEventListener('resize', this.handleResize)
    })
  },

  beforeDestroy() {
    // 销毁 ECharts 实例
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
    // 移除窗口监听事件
    window.removeEventListener('resize', this.handleResize)
  },

  methods: {
    /**
     * 计算数据总和
     */
    calculateTotal(data) {
      return data.reduce((sum, item) => Number(sum) + (Number(item.value) || 0), 0)
    },

    /**
     * 初始化图表
     */
    initChart(data) {
      if (!data || !data.length || data.length === 0) {
        return
      }

      // 销毁已有实例
      if (this.chart) {
        this.chart.dispose()
        this.chart = null
      }

      // 为数据项添加颜色
      const coloredData = data.map(item => ({
        ...item,
        itemStyle: {
          // 注意：这里调用的是导入的 generateRandomColor
          color: generateRandomColor(this.defaultColor) // 使用 this.defaultColor
        }
      }))

      // 计算总数
      const total = this.calculateTotal(data)

      // 初始化图表 (使用 this.$refs.chartRef)
      this.chart = echarts.init(this.$refs.chartRef, 'macarons')

      const option = {
        title: {
          text: this.chartTitle,
          textStyle: {
            fontSize: 16,
            color: '#2e95f3'
          },
          top: '5%',
          left: '2%'
        },
        tooltip: {
          trigger: 'item',
          // 格式化tooltip，添加总数显示
          // 'total' 变量在 formatter 的闭包中是可用的
          formatter: function(params) {
            return `${params.seriesName} <br/>${params.name} : ${params.value} (${params.percent.toFixed(1)}%)<br/>总数: ${total}`
          }
        },
        legend: {
          type: 'scroll', // 启用滚动
          orient: 'horizontal', // 水平排列
          height: 100 ,
          left: '5%',   // 从左侧 5% 的位置开始
          right: '5%',  // 在右侧 5% 的位置结束，这样图例的可用宽度就是 90%
          bottom: '5%',
          itemGap: 5,
          textStyle: { fontSize: 14, color: '#ffffff' } // 原 legend 颜色
        },
        series: [
          {
            name: this.chartTitle, // 使用 this.chartName
            type: 'pie',
            roseType: 'radius',
            top: '10%',
            radius: [20, 95],
            center: ['50%', '38%'],
            label: {
              formatter: '{b}',
              textStyle: {
                fontSize: 14,
                color: '#2e95f3'
              }
            },
            data: coloredData,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
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
/* 可选：添加一个默认样式，确保 div 至少有基本布局 */
.chart {
  overflow: hidden;
}
</style>
