<template>
  <div :class="className" :style="{ height, width }" ref="chartRef"></div>
</template>

<script>
import * as echarts from 'echarts'
import 'echarts/theme/macarons' // 引入 'macarons' 主题

// 定义 ECharts 实例名称
const CHART_NAME = 'PieLayerRateCharts'

export default {
  name: CHART_NAME, // 组件名称设置为 PieLayerRateCharts

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
    },
    // 核心数据
    chartData: {
      type: Array,
      default: () => [
        {"name": "场馆运行", "value": 5},
        {"name": "场馆安全", "value": 5},
        {"name": "医疗服务安全", "value": 6},
      ]
    },
    // 标题
    chartTitle: {
      type: String,
      default: '事件总数'
    }
  },

  data() {
    return {
      chart: null, // ECharts 实例
      // 饼图中心点，为了给底部图例留出空间
      pieCenter: ['50%', '45%'],
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
    // 侦听 defaultColor 变化
    defaultColor: {
      deep: true,
      handler() {
        this.setOptions(this.chartData)
      }
    }
  },

  mounted() {
    this.$nextTick(() => {
      this.initChart()
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
     * 动态生成颜色数组（根据数据量自动循环）
     * @param {Array} baseColors - 基础颜色列表 (this.defaultColor)
     */
    generateColorList(baseColors, dataLength) {
      let result = [];
      for (let i = 0; i < dataLength; i++) {
        // 【使用传入的颜色列表】
        result.push(baseColors[i % baseColors.length]);
        result.push(''); // 间隔
      }
      return result;
    },

    /**
     * 动态生成半透明颜色数组
     * @param {Array} baseColors - 基础颜色列表 (this.defaultColor)
     */
    generateColorList2(baseColors, dataLength) {
      let result = [];
      for (let i = 0; i < dataLength; i++) {
        // 【使用传入的颜色列表】
        const color = baseColors[i % baseColors.length];
        const hex = color.startsWith('#') ? color.slice(1) : color;
        // 转换 HEX 到 RGBA (需要处理 3 位和 6 位 HEX)
        let rgba = [];
        if (hex.length === 3) {
          rgba = hex.split('').map(x => parseInt(x + x, 16));
        } else if (hex.length === 6) {
          rgba = hex.match(/.{2}/g).map(x => parseInt(x, 16));
        } else {
          // 如果颜色格式不标准，使用默认值
          rgba = [0, 0, 0];
        }

        // 使用 0.4 透明度
        result.push(`rgba(${rgba[0]},${rgba[1]},${rgba[2]}, 0.4)`);
        result.push(''); // 间隔
      }
      return result;
    },

    /**
     * 初始化图表实例
     */
    initChart() {
      if (!this.$refs.chartRef) return

      if (this.chart) {
        this.chart.dispose()
        this.chart = null
      }

      this.chart = echarts.init(this.$refs.chartRef, 'macarons')
      this.setOptions(this.chartData)
    },

    /**
     * 设置 ECharts 配置项
     */
    setOptions(data) {
      if (!this.chart || !data.length) return

      // --- 1. 预处理数据和颜色 ---
      // 【使用 this.defaultColor 属性】
      const baseColors = this.defaultColor;
      const colorList1 = this.generateColorList(baseColors, data.length);
      const colorList2 = this.generateColorList2(baseColors, data.length);

      let valueSum = 0;
      let optionData = []
      data.forEach(item => {
        valueSum += item.value;
        optionData.push({value: item.value, name: item.name});
        optionData.push({name: '', value: valueSum / 100, itemStyle: {color: 'transparent'}});
      })

      const valueOnlyData = data.map(item => item.value);

      // --- 2. 尺寸和中心配置 ---
      const PIE_CENTER = this.pieCenter;
      const RADIUS_INNER_RING = ['38%', '59%'];
      const RADIUS_OUTER_RING = ['62%', '64%'];
      const RADIUS_DECORATION_1 = ['68.5%', '68.7%'];
      const RADIUS_DECORATION_2 = ['68.5%', '69.1%'];


      const option = {
        // 【设置 ECharts 默认颜色列表，确保图例的默认颜色和鼠标悬停效果】
        color: baseColors,
        tooltip: {
          trigger: 'item',
          backgroundColor: 'transparent',
          borderColor: 'transparent',
          textStyle: {color: '#fff'},
          formatter: function (params) {
            if (params.name === '') return '';
            const value = params.value;
            const percent = ((value / valueSum) * 100).toFixed(2);
            return `${params.name}<br/>数值：${value} (${percent}%)`;
          }
        },

        title: {
          text: '{a|' + valueSum + '}{b|\n' + this.chartTitle + '}',
          left: '49.5%',
          top: '39%',
          itemGap: 10,
          textStyle: {
            rich: {
              a: {color: '#2793FF', fontSize: 36, fontWeight: 600},
              b: {color: '#ffffff', fontSize: 14}
            }
          },
          textAlign: 'center',
        },

        legend: {
          show: true,
          icon: 'none',
          type: 'scroll',
          orient: 'horizontal',
          bottom: '10%',
          left: 'center',
          maxHeight: 50,
          itemWidth: 12,
          itemHeight: 12,
          itemGap: 15,
          inactiveColor: '#666',

          pageButtonItemGap: 10,
          pageButtonGap: 10,
          pageIconColor: '#2793FF',
          pageIconInactiveColor: '#666',
          pageIconSize: 12,
          pageTextStyle: {color: '#FFF', fontSize: 12},

          formatter: (name) => {
            return `{iconName|}{name|${name}}`
          },
          textStyle: {
            color: '#FFF',
            fontSize: 16,
            rich: {
              name: {color: 'inherit', fontSize: 16, width: 80, padding: [0, 5, 0, 5]}
            }
          },
          data: data.map((dItem, dIndex) => {
            return {
              name: dItem.name,
              textStyle: {
                rich: {
                  iconName: {
                    width: 16,
                    height: 16,
                    borderRadius: 2,
                    // 【关键修改：使用 this.defaultColor】
                    backgroundColor: baseColors[dIndex % baseColors.length],
                  }
                }
              }
            }
          }),
        },

        series: [
          // --- 实际数据系列（内部环，使用半透明颜色）---
          {
            type: 'pie', zlevel: 3, radius: RADIUS_INNER_RING, center: PIE_CENTER,
            legendHoverLink: true,
            itemStyle: {
              normal: {
                color: function (params) {
                  return colorList2[params.dataIndex]
                }
              }
            },
            label: {
              show: true, position: 'outside',
              formatter: function (params) {
                if (params.name === '') return '';
                return params.name;
              },
              color: '#FFF', fontSize: 14
            },
            labelLine: {show: true, length: 10, length2: 20, lineStyle: {color: '#FFF', width: 1}},
            data: optionData.map(item => {
              if (item.name === '') {
                return {...item, label: {show: false}, labelLine: {show: false}};
              }
              return item;
            })
          },
          // --- 外部环（实色）---
          {
            type: 'pie', zlevel: 1, radius: RADIUS_OUTER_RING, center: PIE_CENTER,
            legendHoverLink: true,
            itemStyle: {
              normal: {
                color: function (params) {
                  // 【使用实色列表】
                  return colorList1[params.dataIndex]
                }
              }
            },
            label: {show: false},
            data: optionData,
          },
          // --- 装饰圈 1 (细线) ---
          {
            type: "pie", radius: RADIUS_DECORATION_1, center: PIE_CENTER,
            hoverAnimation: false, clockWise: false,
            itemStyle: {
              normal: {
                shadowBlur: 1, shadowColor: "rgba(15, 79, 150,0.61)",
                color: "rgba(23,138,173,1)",
              }
            },
            label: {show: false},
            data: [0]
          },
          // --- 装饰圈 2 (虚线/分隔) ---
          {
            type: "pie", radius: RADIUS_DECORATION_2, center: PIE_CENTER,
            hoverAnimation: false, clockWise: false,
            color: ["#55c2e200", "rgba(23,138,173,1)", "#ff5a6100", "ff5a6100"],
            label: {show: false},
            data: valueOnlyData
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
/* 确保图表容器有正确的布局 */
.chart {
  overflow: hidden;
}
</style>
