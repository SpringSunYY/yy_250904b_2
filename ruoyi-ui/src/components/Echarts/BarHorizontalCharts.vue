<template>
  <div :class="className" :style="{ height, width }" ref="chartRef"></div>
</template>

<script>
// 引入 ECharts 核心模块
import * as echarts from 'echarts';

export default {
  name: 'BarHorizontalCharts', // 组件名称设置为 BarHorizontalCharts

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
    // 核心数据：包含 names (Y轴类目) 和 values (X轴数值)
    chartData: {
      type: Object,
      default: () => ({
        names: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        values: [2220, 1682, 2791, 3000, 4090, 3230, 2910]
      })
    },
    chartTitle: { type: String, default: 'bar-horizontal-charts' },
    chartName: { type: String, default: '' }
  },

  data() {
    return {
      chart: null // ECharts 实例
    };
  },

  watch: {
    // 深度侦听 chartData 的变化
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val);
      }
    },
    // 侦听容器宽高的变化
    width() {
      this.$nextTick(() => this.resizeChart());
    },
    height() {
      this.$nextTick(() => this.resizeChart());
    }
  },

  mounted() {
    this.$nextTick(() => {
      this.initChart();
      window.addEventListener('resize', this.handleResize);
    });
  },

  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
      this.chart = null;
    }
    window.removeEventListener('resize', this.handleResize);
  },

  methods: {
    /**
     * 初始化图表实例
     */
    initChart() {
      if (!this.$refs.chartRef) return;

      if (this.chart) {
        this.chart.dispose();
        this.chart = null;
      }

      this.chart = echarts.init(this.$refs.chartRef);
      this.setOptions(this.chartData);
    },

    /**
     * 设置 ECharts 配置项
     * @param {Object} data - 从 this.chartData 传入的数据
     */
    setOptions(data) {
      if (!this.chart) return;
      if (!data || !data.names || !data.values) {
        console.warn("ECharts: 传入的 chartData 无效。");
        return;
      }

      const { names, values } = data;

      const option = {
        title: {
          text: this.chartTitle,
          textStyle: {
            color: '#ccc'
          },
          left:'2%'
        },
        // === Tooltip 配置 (透明背景, 标题) ===
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          // 设置背景为半透明深色
          backgroundColor: 'rgba(30, 30, 30, 0.7)',
          borderWidth: 0,
          padding: [10, 15],
          textStyle: {
            color: '#fff',
          },
          // 自定义格式，将 Y 轴类目名作为标题
          formatter: function (params) {
            const categoryName = params[0].name;
            const value = params[0].value;

            return `
                <div style="font-size: 16px; font-weight: bold; margin-bottom: 5px;">${categoryName}</div>
                ${params[0].marker} ${value}
            `;
          }
        },
        // ======================================

        barWidth: 10,
        grid: { // 增加 grid 属性以确保图表居中和留白
          left: '3%',
          right: '5%',
          bottom: '3%',
          top: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          splitLine: {
            lineStyle: {
              color: "rgba(255,255,255,0.2)",
              type: 'dashed'
            }
          },
          axisTick: { show: false },
          axisLabel: {
            textStyle: {
              color: "rgba(250,250,250,0.6)",
              fontSize: 16
            }
          }
        },
        yAxis: {
          type: 'category',
          data: names, // 使用传入的 names
          splitLine: { show: false },
          axisTick: { show: false },
          axisLine: {
            lineStyle: { color: '#2c4054' }
          },
          axisLabel: {
            textStyle: {
              color: "rgba(250,250,250,0.6)",
              fontSize: 16
            }
          }
        },
        series: [{
          type: 'bar',
          name: this.chartName,
          barWidth: 20,
          itemStyle: {
            normal: {
              label: {
                show: true,
                position: "insideRight",
                textStyle: {
                  color: "#FFFFFF",
                  fontSize: 16,
                  fontWeight: 600
                }
              },
              // 渐变色配置
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                offset: 0,
                color: 'rgba(24,144,255,1)'
              }, {
                offset: 1,
                color: 'rgba(24,144,255,0)'
              }]),
              barBorderRadius: 15
            }
          },
          data: values // 使用传入的 values
        }]
      };

      // 应用配置项，使用 true 确保完全替换
      this.chart.setOption(option, true);
    },

    /**
     * 处理窗口大小变化，重绘图表
     */
    handleResize() {
      this.chart?.resize();
    }
  }
};
</script>

<style scoped>
/* 确保图表容器有正确的布局 */
.chart {
  overflow: hidden;
}
</style>
