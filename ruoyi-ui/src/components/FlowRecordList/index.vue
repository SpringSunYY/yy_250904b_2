<template>
  <el-dialog
    title="流程流转记录"
    :visible="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    width="70%"
    @close="handleClose"
  >
    <!--flowRecordList-->
      <div>
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

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleClose">关闭</el-button>
      </div>
  </el-dialog>
</template>

<script>
import { flowRecord } from '@/api/flowable/finished'

export default {
  name: 'FlowRecordList',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    deployId: {
      type: String,
      default: ''
    },
    procInsId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      flowRecordList: []
    }
  },
  methods: {
    /** 流程流转记录 */
    getFlowRecordList(procInsId, deployId) {
      // 如果参数为空则不请求
      if (!procInsId || !deployId) {
        return
      }

      const that = this
      this.flowRecordList = []
      const params = { procInsId: procInsId, deployId: deployId }
      flowRecord(params).then(res => {
        that.flowRecordList = res.data.flowList
      }).catch(() => {
        this.$message.error('获取流程记录失败')
      })
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
    handleClose() {
      this.$emit('update:visible', false)
    }
  },
  watch: {
    visible: {
      handler(newVal) {
        if (newVal) {
          this.getFlowRecordList(this.procInsId, this.deployId)
        }
      },
      immediate: true
    }
  }
}
</script>

<style scoped lang="scss">

</style>
