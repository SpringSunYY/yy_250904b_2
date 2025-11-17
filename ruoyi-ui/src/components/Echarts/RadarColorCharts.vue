<template>
  <div :class="className" :style="{ height, width }" ref="chartRef"/>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'RadarColorCharts', // 建议为组件命名
  props: {
    className: {type: String, default: 'chart'},
    width: {type: String, default: '100%'},
    height: {type: String, default: '100%'},
    chartData: {
      type: Object,
      default: () => ({
        indicators: ['小型车', '中型车', '大型车', '货车', '特种车', '贵宾车'],
        datas: [
          {name: 'SOC', values: [3600, 3900, 4300, 4700, 3800, 4200]},
          {name: '电流', values: [4300, 4700, 3600, 3900, 3800, 4200]},
          {name: '电压', values: [3200, 3000, 3400, 2000, 3900, 2000]}
        ]
      })
    },
    defaultColor: {
      type: Array,
      default: () => ['#FA9D47', '#0496FF', '#00FBFF', '#4BFFFC', '#816d85', '#FFB74A']
    },
    legendPosition: {
      type: Object,
      default: () => ({top: 'center', right: 10})
    },
    radarCenter: {
      type: Array,
      default: () => ['50%', '50%']
    },
    radarRadius: {
      type: Array,
      default: () => ['0', '65%']
    },
    chartName: {type: String, default: '车辆雷达图'},
  },
  data() {
    return {
      chart: null,
      resizeObserver: null,
      // 用于存储 buildSeries 的结果，以便在 tooltip formatter 中访问
      currentBuildResult: null,
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler() {
        this.$nextTick(() => {
          this.initChart()
        })
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
      this.observeResize()
    })
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
    if (this.resizeObserver && this.$refs.chartRef) {
      this.resizeObserver.unobserve(this.$refs.chartRef)
      this.resizeObserver = null
    }
  },
  methods: {
    // 检查数组中所有值是否都为0
    allValuesAreZero(values) {
      return values.every(v => Number(v) === 0)
    },

    // 构建 series
    buildSeries(indicators, datas) {
      const validIndicatorIndexes = []
      indicators.forEach((indicator, index) => {
        const hasValidData = datas.some(series => Number(series.values[index]) !== 0)
        if (hasValidData) {
          validIndicatorIndexes.push(index)
        }
      })

      if (validIndicatorIndexes.length === 0) {
        return {series: [], indicators: [], legendData: [], colorArray: []}
      }

      const filteredIndicators = validIndicatorIndexes.map(index => indicators[index])
      const filteredDatas = datas.map((series, idx) => ({
        name: series.name,
        values: validIndicatorIndexes.map(index => series.values[index]),
        // 注意：这里访问 props 需要用 this
        color: this.defaultColor[idx % this.defaultColor.length]
      }))

      const allValues = filteredDatas.flatMap(d => d.values)
      const absoluteMax = allValues.length > 0 ? Math.max(...allValues) : 0

      let max
      if (absoluteMax === 0) {
        max = 100
      } else if (absoluteMax < 10) {
        max = Math.ceil(absoluteMax) + 1
      } else if (absoluteMax < 100) {
        max = Math.ceil(absoluteMax / 10) * 10
      } else if (absoluteMax < 500) {
        max = Math.ceil(absoluteMax / 50) * 50
      } else {
        max = Math.ceil(absoluteMax / 100) * 100
      }

      // 确保 max 留有缓冲区
      if (max <= absoluteMax) {
        max = absoluteMax + Math.max(1, Math.ceil(absoluteMax * 0.1));
      }

      const indicatorObjects = filteredIndicators.map(name => ({name, max}))

      const seriesData = filteredDatas
        .filter(item => !this.allValuesAreZero(item.values)) // 使用 this.allValuesAreZero
        .map(item => ({
          value: item.values,
          name: item.name,
          itemStyle: {
            color: item.color,
            opacity: 0.8,
            borderWidth: 1,
            borderColor: item.color,
          },
          lineStyle: {
            color: item.color,
          },
          areaStyle: {
            color: item.color,
            opacity: 0.5,
          }
        }))

      const finalDatas = filteredDatas.filter(item => !this.allValuesAreZero(item.values)) // 使用 this.allValuesAreZero
      const legendData = finalDatas.map(item => item.name)
      const colorArray = finalDatas.map(item => item.color)

      return {
        series: seriesData,
        indicators: indicatorObjects,
        legendData,
        colorArray
      }
    },

    // 初始化图表
    initChart() {
      // 访问 props 和 refs 时使用 this
      if (!this.chartData || !this.chartData.indicators || !this.chartData.datas || !this.$refs.chartRef) return

      if (this.chart) {
        this.chart.dispose()
        this.chart = null
      }

      this.chart = echarts.init(this.$refs.chartRef)

      const {indicators, datas} = this.chartData
      const buildResult = this.buildSeries(indicators, datas) // 使用 this.buildSeries
      this.currentBuildResult = buildResult // 存储结果供 tooltip 使用

      const option = {
        title: {
          text: this.chartName, // 使用 this.chartName
          textStyle: {color: '#00E4FF', fontSize: 16},
          top: '5%',
          left: '2%'
        },
        color: buildResult.colorArray,
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(3, 9, 24, 1)',
          textStyle: {
            color: 'rgba(255, 255, 255, 1)',
          },
          formatter: (params) => {
            const result = this.currentBuildResult // 使用 this.currentBuildResult
            if (!result) return ''

            const seriesName = params.name
            const values = params.value

            const marker = `<span style="display:inline-block;margin-right:4px;border-radius:10px;width:10px;height:10px;background-color:${params.color};"></span>`

            let text = `<strong style="font-size: 16px;">${marker}${seriesName}</strong><br/>`

            values.forEach((v, i) => {
              const indicator = result.indicators[i]
              if (indicator && v !== 0) {
                text += `${indicator.name}: ${v}<br/>`
              }
            })
            return text
          }
        },
        legend: {
          orient: 'vertical',
          icon: 'rectangle',
          data: buildResult.legendData,
          top: this.legendPosition.top, // 使用 this.legendPosition
          right: this.legendPosition.right,
          itemWidth: 14,
          itemHeight: 14,
          itemGap: 21,
          textStyle: {
            fontSize: 14,
            color: '#fff',
          },
        },
        radar: {
          radius: this.radarRadius, // 使用 this.radarRadius
          center: this.radarCenter, // 使用 this.radarCenter
          axisName: {
            color: '#fff',
            fontSize: 14,
          },
          splitNumber: 4,
          indicator: buildResult.indicators,
          splitArea: {
            show: true,
            areaStyle: {
              color: ['rgba(255, 255, 255, 0.05)', 'rgba(255, 255, 255, 0.1)'],
            },
          },
          axisLine: {
            lineStyle: {
              color: '#ffffff',
              opacity: 0.2,
            },
          },
          splitLine: {
            lineStyle: {
              color: '#fff',
              opacity: 0.4,
              width: 1,
              shadowColor: '#0496FF',
              shadowBlur: 5,
            },
          },
        },
        series: [
          {
            type: 'radar',
            symbolSize: 8,
            data: buildResult.series,
            name: '雷达系列',
            emphasis: {
              lineStyle: {
                width: 4
              }
            },
            label: {
              show: false
            }
          },
        ],
      }

      this.chart.setOption(option, true)
    },

    // 统一的自适应处理函数
    handleResize() {
      if (this.chart) {
        this.chart.resize()
      }
    },

    // 观察容器大小变化
    observeResize() {
      if (!this.$refs.chartRef) return
      this.resizeObserver = new ResizeObserver(() => {
        // 在 Vue 2 中，requestAnimationFrame 仍然是好的实践
        requestAnimationFrame(this.handleResize)
      })
      this.resizeObserver.observe(this.$refs.chartRef)
    }
  }
}
</script>

<style scoped>
.chart {
  width: 100%;
  height: 100%;
}
</style>
