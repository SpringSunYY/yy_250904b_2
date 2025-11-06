<template>
  <div class="notice-detail-container" v-if="data">
    <h2 class="detail-title">{{ data.noticeTitle }}</h2>

    <el-row :gutter="20" class="meta-info">
      <el-col :span="6">
        <label>公告类型:</label>
        <dict-tag :options="dict.type.sys_notice_type" :value="data.noticeType"/>
      </el-col>
      <el-col :span="6">
        <label>创建人:</label>
        <span class="meta-value">{{ data.createBy }}</span>
      </el-col>
      <el-col :span="6">
        <label>创建时间:</label>
        <span class="meta-value">{{ data.createTime }}</span>
      </el-col>
    </el-row>

    <div class="content-section">
      <h3>公告内容</h3>
      <div class="notice-content" v-html="data.noticeContent"></div>
    </div>
  </div>
  <div v-else>
    <el-empty description="未找到公告信息"></el-empty>
  </div>
</template>
<script>
import {getNotice} from '@/api/system/notice'

export default {
  name: 'NoticeDetail',
  dicts: ['sys_notice_type'],
  data() {
    return {
      noticeId: null,
      // 表单参数
      data: {}
    }
  },
  created() {
    this.noticeId = this.$route.params && this.$route.params.noticeId
    console.log(this.noticeId)
    this.getNoticeInfo(this.noticeId)
  },
  methods: {
    /** 查询公告详细信息 */
    getNoticeInfo(noticeId) {
      if (!noticeId) {
        this.$modal.info('请选择需要查看的数据')
      }
      getNotice(noticeId).then(response => {
        this.data = response.data
      })
    }
  }
}
</script>

<style scoped lang="scss">
.notice-detail-container {
  padding: 20px;
  line-height: 1.8;
  color: #303133;
}

.detail-title {
  text-align: center;
  margin-bottom: 25px;
  font-size: 24px;
  color: #303133;
}

.meta-info {
  border-bottom: 1px solid #EBEEF5;
  padding-bottom: 15px;
  margin-bottom: 20px;
  font-size: 14px;
  color: #606266;
  display: flex; /* 使用 flex 确保对齐 */
  align-items: center;
}

.meta-info .el-col {
  display: flex;
  align-items: center;
}

.meta-info label {
  font-weight: bold;
  margin-right: 8px;
  color: #303133;
  flex-shrink: 0; /* 防止标签换行 */
}

.meta-value {
  color: #606266;
}

.content-section h3 {
  font-size: 16px;
  margin-bottom: 15px;
  padding-left: 10px;
  border-left: 4px solid #409EFF; /* 使用 Element UI 主题色 */
}

.notice-content {
  min-height: 192px; /* 与编辑器的最小高度保持一致 */
  padding: 10px;
  border: 1px solid #DCDFE6;
  border-radius: 4px;
  background-color: #FAFAFA;
}
</style>
