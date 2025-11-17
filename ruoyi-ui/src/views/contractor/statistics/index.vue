<template>
  <div class="index">
    <h1 style="text-align: center">
      数据统计
    </h1>
    <div class="query">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="选择项目" prop="choiceId">
          <el-select v-model="queryParams.choiceId" placeholder="请选择"
                     filterable
                     remote
                     reserve-keyword
                     :remote-method="remoteChoiceList"
                     :loading="choiceLoading"
                     clearable>
            <el-option
              v-for="item in choiceList"
              :key="item.choiceId"
              :value="item.choiceId"
              :label="item.selectedContractor"
            >
              <span style="float: left">{{ item.selectedContractor }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px;margin-left: 5px">{{
                  item.projectName
                }}</span>
            </el-option>
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
        <el-col :span="12">
          <div class="chart chart-wrapper">
            <PieLayerRateCharts :chart-data="trainingRateStatisticsData"
                                :chart-title="trainingRateStatisticsName"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="chart chart-wrapper">
            <PieTotalRateCharts :chart-data="toolInventoryRateStatisticsData"
                                :chart-title="toolInventoryRateStatisticsName"
                                :total="toolInventoryRateTotal"
                                :rateValue="100"
                                rateSymbol="%"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="chart chart-wrapper">
            <PieRoseCharts
              :chart-data="qualificationRateData"
              :chart-title="qualificationRateName"/>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="chart chart-wrapper">
            <PieTotalCharts :chart-title="securityAgreementRateName"
                            :chart-data="securityAgreementRateDate"/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import PieRoseCharts from '@/components/Echarts/PieRoseCharts.vue'
import PieChart from '@/views/dashboard/PieChart.vue'
import {checkPermi} from '@/utils/permission'
import PieTotalRateCharts from '@/components/Echarts/PieTotalRateCharts.vue'
import PieLayerRateCharts from "@/components/Echarts/PieLayerRateCharts.vue";
import PieTotalCharts from "@/components/Echarts/PieTotalCharts.vue";
import {listChoice} from "@/api/choice/choice";
import {
  qualificationRate,
  securityAgreementRate,
  toolInventoryRateStatistics,
  trainingRateStatistics
} from "@/api/contractor/statistics";

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
    PieTotalCharts,
    PieLayerRateCharts,
    PieTotalRateCharts,
    PieChart,
    PieRoseCharts,
  },
  dicts: ['equip_source', 'equip_levels', 'equip_dept', 'equip_user', 'equip_status', 'equip_order', 'equip_type', 'equip_location', 'sys_yes_no'],
  data() {
    return {
      trainingRateStatisticsData: [],
      trainingRateStatisticsName: "培训合格率",
      toolInventoryRateStatisticsData: [],
      toolInventoryRateStatisticsName: "工器具检查",
      toolInventoryRateTotal: 0,
      securityAgreementRateDate: [],
      securityAgreementRateName: "协议完成率",
      qualificationRateData: [],
      qualificationRateName: "资质完成率",
      //是否有权限
      hasPerm: false,
      //默认时间为最近六个月,并格式化为yyyy-MM
      dateRange: [
        formatDateToDay(start),
        formatDateToDay(now)
      ],
      dateType: 'year',
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
      },
      choiceList: [],
      choiceQuery: {},
      choiceLoading: false
    }
  },
  created() {
    this.getChoiceList()
    this.getTrainingRateStatisticsData()
    this.getToolInventoryRateStatisticsData()
    this.getSecurityAgreementRateData()
    this.getQualificationRateData()
  },
  methods: {
    checkPermi,
    getQualificationRateData() {
      this.initQueryParams()
      qualificationRate(this.queryParams).then(response => {
        this.qualificationRateData = response.data
      })
    },
    getSecurityAgreementRateData() {
      this.initQueryParams()
      securityAgreementRate(this.queryParams).then(response => {
        this.securityAgreementRateDate = response.data
      })
    },
    getTrainingRateStatisticsData() {
      this.initQueryParams()
      trainingRateStatistics(this.queryParams).then(response => {
        this.trainingRateStatisticsData = response.data
      })
    },
    getToolInventoryRateStatisticsData() {
      this.initQueryParams()
      toolInventoryRateStatistics(this.queryParams).then(response => {
        this.toolInventoryRateStatisticsData = response.data
        if (response.data.length > 0) {
          this.toolInventoryRateTotal = response.data.reduce((acc, cur) => Number(acc) + Number(cur.value), 0)
        }
      })
    },
    //查询选择
    getChoiceList() {
      listChoice().then(response => {
        this.choiceList = response.rows
      })
    },
    remoteChoiceList(keyword) {
      this.choiceQuery.selectedContractor = keyword
      this.choiceLoading = true
      listChoice(this.choiceQuery).then(response => {
        this.choiceList = response.rows
        this.choiceLoading = false
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
      this.getTrainingRateStatisticsData()
      this.getToolInventoryRateStatisticsData()
      this.getSecurityAgreementRateData()
      this.getQualificationRateData()
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
  background-image: url("~@/assets/images/contractor-bg.png");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  min-height: 92vh;
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
  margin: 5px 16px;

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
