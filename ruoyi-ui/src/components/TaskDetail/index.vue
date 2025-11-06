<template>
  <el-dialog
    title="流程流转记录"
    :visible="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    width="80%"
    @close="handleClose"
  >
    <el-card class="box-card">
      <el-tabs tab-position="top" v-model="activeName" @tab-click="handleClick" id="printContent">
        <!--表单信息-->
        <el-tab-pane label="表单信息" name="1">
          <h2 class="print-section-title">表单信息</h2>
          <el-col :span="16" :offset="4" style="padding-top: 12px">
            <v-form-render ref="vFormRef"/>
          </el-col>
        </el-tab-pane>
        <!--流程流转记录-->
        <el-tab-pane label="流转记录" name="2">
          <div class="flow-record-wrapper">
            <el-col :span="16" :offset="4">
              <div class="block">
                <h2 class="print-section-title">流转记录</h2>
                <el-timeline>
                  <el-timeline-item
                    v-for="(item,index ) in flowRecordList"
                    :key="index"
                    :icon="setIcon(item.finishTime)"
                    :color="setColor(item.finishTime)"
                  >
                    <p style="font-weight: 700">{{ item.taskName }}</p>
                    <el-card :body-style="{ padding: '10px' }">
                      <el-descriptions class="margin-top" :column="1" size="small" border>
                        <el-descriptions-item v-if="item.assigneeName" label-class-name="my-label">
                          <template slot="label"><i class="el-icon-user"></i>办理人</template>
                          {{ item.assigneeName }}
                          <el-tag type="info" size="mini">{{ item.deptName }}</el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item v-if="item.candidate" label-class-name="my-label">
                          <template slot="label"><i class="el-icon-user"></i>候选办理</template>
                          {{ item.candidate }}
                        </el-descriptions-item>
                        <el-descriptions-item label-class-name="my-label">
                          <template slot="label"><i class="el-icon-date"></i>接收时间</template>
                          {{ item.createTime }}
                        </el-descriptions-item>
                        <el-descriptions-item v-if="item.finishTime" label-class-name="my-label">
                          <template slot="label"><i class="el-icon-date"></i>处理时间</template>
                          {{ item.finishTime }}
                        </el-descriptions-item>
                        <el-descriptions-item v-if="item.duration" label-class-name="my-label">
                          <template slot="label"><i class="el-icon-time"></i>耗时</template>
                          {{ item.duration }}
                        </el-descriptions-item>
                        <el-descriptions-item v-if="item.comment" label-class-name="my-label">
                          <template slot="label"><i class="el-icon-tickets"></i>处理意见</template>
                          {{ item.comment.comment }}
                        </el-descriptions-item>
                      </el-descriptions>
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-col>
          </div>
        </el-tab-pane>
        <el-tab-pane label="流程图" name="3">
          <Bpmn-viewer :flowData="flowData" :procInsId="taskForm.procInsId"/>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" v-print="'#printContent'">打 印</el-button>
      <el-button type="primary" @click="handleClose">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { flowRecord } from '@/api/flowable/finished'
import { getProcessVariables, flowXmlAndNode } from '@/api/flowable/definition'
import BpmnViewer from '@/components/Process/viewer'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'Record',
  components: {
    BpmnViewer
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    taskId: {
      type: String,
      default: ''
    },
    procInsId: {
      type: String,
      default: ''
    },
    deployId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      flowData: {},
      activeName: '1',
      queryParams: {
        deptId: undefined
      },
      loading: true,
      flowRecordList: [],
      taskForm: {
        multiple: false,
        comment: '',
        procInsId: '',
        instanceId: '',
        deployId: '',
        taskId: '',
        procDefId: '',
        vars: ''
      },
      printConfig: {
        id: 'printContent',
        popTitle: ' ',
        extraCss: `
          @media print {
            @page { margin: 0; }
            body { -webkit-print-color-adjust: exact; }
            button { display: none; }
          }
        `
      }
    }
  },
  created() {
    this.flowData.taskId = this.taskId
    this.flowData.procInsId = this.procInsId
    this.flowData.deployId = this.deployId
    this.getDetail()
  },
  methods: {
    getDetail() {
      if (this.taskId) {
        this.processVariables(this.taskId)
      }
      this.getFlowRecordList(this.procInsId, this.deployId)
    },
    handleClick(tab, event) {
      if (tab.name === '3') {
        flowXmlAndNode({ procInsId: this.procInsId, deployId: this.deployId }).then(res => {
          this.flowData = res.data
        })
      }
    },
    setIcon(val) {
      if (val) {
        return 'el-icon-check'
      } else {
        return 'el-icon-time'
      }
    },
    setColor(val) {
      if (val) {
        return '#2bc418'
      } else {
        return '#b3bdbb'
      }
    },
    getFlowRecordList(procInsId, deployId) {
      const that = this
      const params = { procInsId: procInsId, deployId: deployId }
      this.flowRecordList = []
      flowRecord(params).then(res => {
        that.flowRecordList = res.data.flowList
      }).catch(res => {
      })
    },
    processVariables(taskId) {
      if (taskId) {
        getProcessVariables(taskId).then(res => {
          this.$nextTick(() => {
            this.$refs.vFormRef.setFormJson(res.data.formJson)
            this.$nextTick(() => {
              this.$refs.vFormRef.setFormData(res.data)
              this.$nextTick(() => {
                this.$refs.vFormRef.disableForm()
              })
            })
          })
        })
      }
    },
    handleClose() {
      this.$emit('update:visible', false)
    }
  },
  watch: {
    visible: {
      handler(newVal) {
        this.flowData.taskId = this.taskId
        this.flowData.procInsId = this.procInsId
        this.flowData.deployId = this.deployId
        if (newVal) {
          this.getDetail()
        }
      },
      immediate: true
    }
  }
}
</script>

<style lang="scss" scoped>
.test-form {
  margin: 15px auto;
  width: 800px;
  padding: 15px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.my-label {
  background: #E1F3D8;
}

// 打印标题默认隐藏
.print-section-title {
  display: none;
}

// 打印样式
@media print {
  // 隐藏tab导航
  #printContent ::v-deep .el-tabs__header {
    display: none !important;
  }

  // 隐藏流程图tab
  #printContent ::v-deep .el-tab-pane[aria-labelledby="tab-3"] {
    display: none !important;
  }

  // 强制显示表单信息和流转记录tab，并设置为block布局
  #printContent ::v-deep .el-tab-pane[aria-labelledby="tab-1"],
  #printContent ::v-deep .el-tab-pane[aria-labelledby="tab-2"] {
    display: block !important;
    opacity: 1 !important;
    visibility: visible !important;
  }

  // 流转记录包裹器强制新开一页（而不是tab-pane本身）
  .flow-record-wrapper {
    page-break-before: always !important;
    break-before: page !important;
  }

  // 显示打印标题
  .print-section-title {
    display: block !important;
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    margin-top: 0 !important;
    margin-bottom: 30px !important;
    padding-top: 0 !important;
    padding-bottom: 10px !important;
    border-bottom: 2px solid #409EFF;
    page-break-after: avoid !important;
  }

  // 修复 Element UI 的 radio
  ::v-deep .el-radio__inner {
    display: inline-block !important;
    border: 1px solid #dcdfe6 !important;
    border-radius: 50% !important;
    width: 14px !important;
    height: 14px !important;
    background-color: #FFF !important;
    position: relative !important;
    box-sizing: border-box !important;
    print-color-adjust: exact !important;
    -webkit-print-color-adjust: exact !important;
  }

  ::v-deep .el-radio__input.is-checked .el-radio__inner {
    border-color: #409EFF !important;
    background: #409EFF !important;
  }

  ::v-deep .el-radio__input.is-checked .el-radio__inner::after {
    content: "" !important;
    display: block !important;
    width: 4px !important;
    height: 4px !important;
    border-radius: 50% !important;
    background-color: #FFF !important;
    position: absolute !important;
    left: 50% !important;
    top: 50% !important;
    transform: translate(-50%, -50%) !important;
  }

  // 修复 Element UI 的 checkbox
  ::v-deep .el-checkbox__inner {
    display: inline-block !important;
    border: 1px solid #dcdfe6 !important;
    border-radius: 2px !important;
    width: 14px !important;
    height: 14px !important;
    background-color: #FFF !important;
    position: relative !important;
    box-sizing: border-box !important;
    print-color-adjust: exact !important;
    -webkit-print-color-adjust: exact !important;
  }

  ::v-deep .el-checkbox__input.is-checked .el-checkbox__inner {
    background-color: #409EFF !important;
    border-color: #409EFF !important;
  }

  ::v-deep .el-checkbox__input.is-checked .el-checkbox__inner::after {
    content: "" !important;
    display: block !important;
    border: 1px solid #FFF !important;
    border-left: 0 !important;
    border-top: 0 !important;
    height: 7px !important;
    left: 4px !important;
    position: absolute !important;
    top: 1px !important;
    width: 3px !important;
    transform: rotate(45deg) scaleY(1) !important;
  }

  // 确保背景色打印
  .my-label {
    print-color-adjust: exact !important;
    -webkit-print-color-adjust: exact !important;
  }

  .el-tag {
    print-color-adjust: exact !important;
    -webkit-print-color-adjust: exact !important;
  }

  // 确保表单渲染器内的元素也能正确打印
  ::v-deep .el-form-item {
    page-break-inside: avoid !important;
  }

  // 避免时间线项目被分页打断
  ::v-deep .el-timeline-item {
    page-break-inside: avoid !important;
  }
}
</style>
