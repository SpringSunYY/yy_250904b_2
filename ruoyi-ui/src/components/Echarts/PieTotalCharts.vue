<template>
  <div :class="className" :style="{ height, width }" ref="chartRef"></div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'PieTotalCharts', // 组件名称设置为 PieTotalCharts

  // 定义 Props
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
    // 核心数据 (对应您之前的 rawData)
    chartData: {
      type: Array,
      default: () => [
        {name: '出行', value: 30},
        {name: '购物', value: 40},
        {name: '旅游', value: 50},
        {name: '娱乐', value: 20},
        {name: '餐饮', value: 16},
        {name: '住宿', value: 24}
      ]
    },
    // 标题
    chartTitle: {
      type: String,
      default: '人才总数'
    }
  },

  data() {
    return {
      chart: null, // ECharts 实例
      // 饼图的中心位置，为了与原图配置匹配，固定为 ["40%", "50%"]
      pieCenter: ['40%', '50%']
    }
  },

  watch: {
    // 深度侦听 chartData 的变化
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    },
    // 侦听其他 props 的变化，确保图表更新
    subTitle(val) {
      this.setOptions(this.chartData)
    },
    width() {
      this.$nextTick(() => this.resizeChart())
    },
    height() {
      this.$nextTick(() => this.resizeChart())
    }
  },

  mounted() {
    this.$nextTick(() => {
      this.initChart()
      window.addEventListener('resize', this.handleResize)
    })
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },

  methods: {
    /**
     * 初始化图表实例
     */
    initChart() {
      if (!this.$refs.chartRef) return

      if (this.chart) {
        this.chart.dispose()
        this.chart = null
      }

      this.chart = echarts.init(this.$refs.chartRef)
      this.setOptions(this.chartData)
    },

    /**
     * 设置 ECharts 配置项
     * @param {Array} data - 从 this.chartData 传入的数据
     */
    setOptions(data) {
      if (!this.chart || !data || data.length === 0) return

      // 1. 计算总数
      const sum = data.reduce((per, cur) => per + cur.value, 0)
      // 2. 计算间隙值 (总数的 1%)
      const gap = (1 * sum) / 100

      const pieData1 = []
      const gapData = {
        name: '',
        value: gap,
        itemStyle: {
          color: 'transparent'
        }
      }

      // 构造带间隙的饼图数据
      for (let i = 0; i < data.length; i++) {
        pieData1.push(data[i])
        pieData1.push(gapData)
      }

      // 提取所有名称用于 legend
      const legendData = data.map(item => item.name)

      const option = {
        title: {
          show: true,
          text: sum.toString(), // 动态计算总数
          subtext: this.chartTitle, // 使用 Props
          x: '39.5%',
          y: '42%',
          itemGap: 6,
          textStyle: {
            color: '#fff',
            fontSize: 25,
            fontWeight: '400',
          },
          subtextStyle: {
            color: '#fff',
            fontSize: 18,
            fontWeight: '400',
          },
          textAlign: 'center'
        },
        tooltip: {
          show: true,
          trigger: 'item',
          backgroundColor: 'rgba(30, 48, 80, 0.8)', // 半透明深蓝色背景
          borderColor: '#4A65A7',
          borderWidth: 1,
          padding: [10, 15],
          formatter: function (params) {
            if (params.name === '') {
              return ''
            }
            // 显示名称、数值、比例
            return (
              `<span style="color: ${params.color}; font-size: 14px;">●</span>` +
              `<span style="color: #fff; font-size: 14px;"> ${params.name}：</span>` +
              `<span style="color: #fff; font-size: 14px; font-weight: bold;">${params.value}</span>` +
              `<span style="color: #fff; font-size: 14px;"> (${params.percent.toFixed(1)}%)</span>`
            )
          },
          textStyle: {
            color: '#fff'
          }
        },

        legend: {
          orient: 'vertical',
          icon: 'diamond',
          textStyle: {
            color: '#f2f2f2',
            fontSize: '12px'
          },
          top: '10%',
          right: '5%',
          itemGap: 14,
          itemHeight: 14,
          itemWidth: 14,
          data: legendData
        },
        series: [
          {
            name: '业务分类',
            type: 'pie',
            roundCap: true,
            radius: ['40%', '56%'],
            center: this.pieCenter, // 使用 data 中的 pieCenter
            data: pieData1,
            labelLine: {
              length: 8,
              length2: 16,
              lineStyle: {
                width: 1
              }
            },
            label: {
              show: true,
              fontFamily: 'ysbth',
              position: 'outside',
              padding: [0, -4, 0, -4],
              // 显示名字和百分比
              formatter(params) {
                if (params.name === '') {
                  return ''
                }
                return `${params.name}\n${params.percent.toFixed(0)}%`
              },
              color: '#fff',
              fontSize: '14px',
              lineHeight: 18
            }
          },
          // 辅助圆环：内圈边框
          {
            type: 'pie',
            radius: ['32%', '34%'],
            center: this.pieCenter,
            animation: false,
            hoverAnimation: false,
            data: [
              {
                value: 100
              }
            ],
            label: {
              show: false
            },
            itemStyle: {
              normal: {
                color: '#3BC5EF'
              }
            }
          },
          // 辅助圆环：中心发光效果
          {
            name: '',
            type: 'pie',
            startAngle: 90,
            radius: '32%',
            animation: false,
            hoverAnimation: false,
            center: this.pieCenter,
            itemStyle: {
              normal: {
                labelLine: {
                  show: false
                },
                color: new echarts.graphic.RadialGradient(0.5, 0.5, 1, [
                  {
                    offset: 1,
                    color: 'rgba(50,171,241, 0)'
                  },
                  {
                    offset: 0.5,
                    color: 'rgba(50,171,241, .4)'
                  },
                  {
                    offset: 0,
                    color: 'rgba(55,70,130, 0)'
                  }
                ]),
                shadowBlur: 60
              }
            },
            data: [
              {
                value: 100
              }
            ]
          }
        ]
      }

      this.chart.setOption(option, true)
    },

    /**
     * 处理窗口大小变化，重绘图表
     */
    handleResize() {
      this.chart?.resize()
    },

    /**
     * 外部调用方法：重设图表大小
     */
    resizeChart() {
      this.chart?.resize()
    }
  }
}
</script>

<style scoped>
/* 确保图表容器有正确的布局 */
.chart {
  overflow: hidden;
}
</style>
