<template>
  <div class="index">
    <div v-if="hasPerm"><h1 style="text-align: center">企业设备查询服务</h1>
      <div class="query">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
          <el-form-item label="设备名称" prop="equName">
            <el-select v-model="queryParams.equName" placeholder="请选择设备" clearable>
              <el-option
                v-for="item in ledgerList"
                :key="item.equipId"
                :label="item.equName"
                :value="item.equName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备位号" prop="equCode">
            <el-select v-model="queryParams.equCode" placeholder="请选择设备" clearable>
              <el-option
                v-for="item in ledgerList"
                :key="item.equipId"
                :label="item.equName"
                :value="item.equCode"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择设备类型" clearable>
              <el-option
                v-for="dict in dict.type.equip_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="管理级别" prop="equipLevels">
            <el-select v-model="queryParams.equipLevels" placeholder="请选择管理级别" clearable>
              <el-option
                v-for="dict in dict.type.equip_levels"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="安装位置" prop="installLocation">
            <el-select v-model="queryParams.installLocation" placeholder="请选择安装位置" clearable>
              <el-option
                v-for="dict in dict.type.equip_location"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择设备状态" clearable>
              <el-option
                v-for="dict in dict.type.equip_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="统计类型" prop="dateType">
            <el-select v-model="dateType" placeholder="请选择统计类型" @change="handleDateTypeChange" clearable>
              <el-option
                v-for="item in dateTypeOptions"
                :key="item.value"
                :value="item.value"
                :label="item.label"

              />
            </el-select>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              :type="dateRangeType"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="chart">
        <el-row :gutter="20" class="chart-margin">
          <el-col :span="8">
            <div class="chart-wrapper">
              <SimpleLineChart :chart-title="checkRateTitle" :chart-name="checkRateName"
                               :chart-data="checkRateChartData"
              />
            </div>
          </el-col>
          <el-col :span="8">
            <div class="chart-wrapper">
              <PieCharts :chart-title="runTimeName" :chart-data="runTimeData"/>
            </div>
          </el-col>
          <el-col :span="8">

            <div class="chart-wrapper">
              <PieRoseCharts :chart-title="lubricateName" :chart-data="lubricateData"/>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20" class="chart-margin">
          <el-col :span="8">
            <div class="chart-wrapper">
              <BarRateCharts :chart-title="defectEliminateName" :chart-data="defectEliminateData"
                             :inner-name="innerName" :outer-name="outerName"
              />
            </div>
          </el-col>
          <el-col :span="8">
            <div class="chart-wrapper">
              <!--              <PieCharts :chart-title="leakageName"/>-->
              <PieTotalRateCharts :chart-title="leakageRateName" :chart-data="leakageRateData"
                                  :total="leakageRateTotal"
              />
            </div>
          </el-col>
          <el-col :span="8">
            <div class="chart-wrapper">
              <!--              <Bar3DStereoscopicCharts :chart-title="defectEliminateStatisticsName"/>-->
              <BarLineRateCharts :chart-title="defectEliminateRateName" :chart-data="defectEliminateRateData"
              :outer-name="defectEliminateRateOuterName" :inner-name="defectEliminateRateInnerName"/>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-else>
      <div style="text-align: center">
        <h1 style="text-align: center">设备管理系统</h1>
      </div>
    </div>
  </div>
</template>

<script>
import SimpleLineChart from '@/components/Echarts/SimpleLineChart.vue'
import PieCharts from '@/components/Echarts/PieCharts.vue'
import BarHorizontalCharts from '@/components/Echarts/BarHorizontalCharts.vue'
import PieRoseCharts from '@/components/Echarts/PieRoseCharts.vue'
import PieChart from '@/views/dashboard/PieChart.vue'
import Bar3DStereoscopicCharts from '@/components/Echarts/Bar3DStereoscopicCharts.vue'
import {checkPermi} from '@/utils/permission'
import {
  checkRateStatistics,
  defectEliminateRateStatistics,
  defectEliminateStatistics,
  leakageRateStatistics,
  lubricateRateStatistics,
  runTimeStatistics
} from '@/api/statistics/statistics'
import BarRateCharts from '@/components/Echarts/BarRateCharts.vue'
import PieTotalRateCharts from '@/components/Echarts/PieTotalRateCharts.vue'
import BarLineRateCharts from '@/components/Echarts/BarLineRateCharts.vue'
import {listLedger} from '@/api/equip/ledger'

const now = new Date()
const start = new Date(now.getFullYear(), now.getMonth() - 5, 1)

// 格式化函数（输出 yyyy-MM）
function formatDateToDay(date) {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

export default {
  name: 'index',
  components: {
    BarLineRateCharts,
    PieTotalRateCharts,
    BarRateCharts,
    Bar3DStereoscopicCharts,
    PieChart,
    PieRoseCharts,
    BarHorizontalCharts,
    PieCharts,
    SimpleLineChart
  },
  dicts: ['equip_source', 'equip_levels', 'equip_dept', 'equip_user', 'equip_status', 'equip_order', 'equip_type', 'equip_location', 'sys_yes_no'],
  data() {
    return {
      // 设备台账列表
      ledgerList: [],
      //是否有权限
      hasPerm: false,
      //设备点检完成率
      checkRateTitle: '设备点检完成率',
      checkRateName: '完成率',
      checkRateData: {},
      checkRateChartData: { names: [], values: [] },
      //设备运行时间统计
      runTimeName: '设备运行时间统计',
      runTimeData: [],
      //设备润滑执行率
      lubricateName: '设备润滑执行率',
      lubricateData: [],
      //设备隐患整改率
      defectEliminateName: '设备隐患整改率',
      defectEliminateData: {},
      innerName: '已完成次数',
      outerName: '应检查次数',
      //设备泄露率
      leakageRateName: '设备泄露率',
      leakageRateData: [],
      leakageRateTotal: 0,
      //设备缺陷消除统计
      defectEliminateRateName: '设备缺陷消除统计',
      defectEliminateRateData: {},
      defectEliminateRateInnerName: '已消除',
      defectEliminateRateOuterName: '已发现',
      //默认时间为最近六个月,并格式化为yyyy-MM
      dateRange: [
        formatDateToDay(start),
        formatDateToDay(now)
      ],
      dateType: 'month',
      dateRangeType: 'monthrange',
      dateTypeOptions: [
        {
          label: '日',
          value: 'day'
        },
        {
          label: '月',
          value: 'month'
        },
        {
          label: '季度',
          value: 'quarter'
        },
        {
          label: '年',
          value: 'year'
        }
      ],
      // 查询参数
      queryParams: {
        equName: null,
        equCode: null,
        type: null,
        equipLevels: null,
        installLocation: null,
        status: null
      },
      pickerOptions: {
        shortcuts: [{
          text: '本月',
          onClick(picker) {
            picker.$emit('pick', [new Date(), new Date()])
          }
        }, {
          text: '今年至今',
          onClick(picker) {
            const end = new Date()
            const start = new Date(new Date().getFullYear(), 0)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setMonth(start.getMonth() - 3)
            picker.$emit('pick', [start, end])
          }
        },
          {
            text: '最近六个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setMonth(start.getMonth() - 6)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一年',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setFullYear(start.getFullYear() - 1)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近五年',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setFullYear(start.getFullYear() - 5)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      }
    }
  },
  created() {
    this.hasPerm = checkPermi(['statistics'])
    if (this.hasPerm) {
      this.getCheckRateData()
      this.getRuntimeDate()
      this.getLubricateRateDate()
      this.getDefectEliminateRateDate()
      this.getLeakageRateDate()
      this.getDefectEliminateRate()
      this.getLedgerList()
    }
  },
  methods: {
    checkPermi,
    /** 查询设备台账列表 */
    getLedgerList() {
      listLedger({ pageSize: 10000 }).then(response => {
        this.ledgerList = response.rows
      })
    },
    getCheckRateData() {
      this.initQueryParams()
      checkRateStatistics(this.queryParams).then(response => {
        this.checkRateData = response.data
        // 处理图表数据
        if (this.checkRateData && this.checkRateData.names && this.checkRateData.values) {
          this.checkRateChartData = {
            names: this.checkRateData.names,
            values: this.checkRateData.values
          }
        }
      })
    },
    getRuntimeDate() {
      this.initQueryParams()
      runTimeStatistics(this.queryParams).then(response => {
        this.runTimeData = response.data
      })
    },
    //获取设备润滑率
    getLubricateRateDate() {
      this.initQueryParams()
      lubricateRateStatistics(this.queryParams).then(response => {
        this.lubricateData = response.data
      })
    },
    //设备隐患整改率
    getDefectEliminateRateDate() {
      this.initQueryParams()
      defectEliminateStatistics(this.queryParams).then(response => {
        this.defectEliminateData = response.data
      })
    },
    //设备泄露率
    getLeakageRateDate() {
      this.initQueryParams()
      leakageRateStatistics(this.queryParams).then(response => {
        this.leakageRateData = response.data.data
        this.leakageRateTotal = response.data.total
      })
    },
    //设备缺陷消除统计
    getDefectEliminateRate() {
      this.initQueryParams()
      defectEliminateRateStatistics(this.queryParams).then(response => {
        this.defectEliminateRateData = response.data
        console.log(this.defectEliminateRateData)
      })
    },
    /**初始化时间*/
    initQueryParams() {
      if (this.dateRange && this.dateRange.length > 0) {
        this.queryParams.startDate = this.dateRange[0]
        this.queryParams.endDate = this.dateRange[1]
      }
      this.queryParams.dateType = this.dateType
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getCheckRateData()
      this.getRuntimeDate()
      this.getLubricateRateDate()
      this.getDefectEliminateRateDate()
      this.getLeakageRateDate()
      this.getDefectEliminateRate()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [formatDateToDay(start), formatDateToDay(now)]
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleDateTypeChange() {
      if (this.dateType === 'day') {
        this.dateRangeType = 'daterange'
      } else {
        this.dateRangeType = 'monthrange'
      }
    }
  }
}
</script>

<style scoped lang="scss">
.index {
  background-image: url("../assets/images/index-bg.png");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  min-height: 92.2vh;
  margin-top: -10px; // 抵消默认间距
  padding-top: 10px; // 添加适当内边距

  h1 {
    padding-top: 1vh;
    color: white;
  }

  .query {
    margin: 0 auto;
    padding: 10px 16px 0;
    height: 8vh;
  }
}

.chart {
  margin: 12px 16px;

  .chart-margin {
    margin-top: 2vh;
  }

  .chart-wrapper {
    height: 33.5vh;
  }
}


@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
