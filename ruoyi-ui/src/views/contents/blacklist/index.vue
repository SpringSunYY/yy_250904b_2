<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['contents:blacklist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['contents:blacklist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['contents:blacklist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contents:blacklist:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="blacklistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="blackId" v-if="columns[0].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[1].visible" />
      <el-table-column label="列入时间" align="center" prop="blacklistDate" width="180" v-if="columns[2].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.blacklistDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="列入事由" align="center" prop="blacklistReason" v-if="columns[3].visible" />
      <el-table-column label="处罚金额（万元）" align="center" prop="penaltyAmount" v-if="columns[4].visible" />
      <el-table-column label="期限（月）" align="center" prop="penaltyPeriod" v-if="columns[5].visible" />
      <el-table-column label="解除日期" align="center" prop="releaseDate" width="180" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.releaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="相关附件" align="center" prop="attachment" v-if="columns[7].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.attachment && scope.row.attachment.length > 0">
            <div v-for="(file, index) in scope.row.attachment.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[8].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[10].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contents:blacklist:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contents:blacklist:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改承包商黑名单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="承包商" prop="contractorName">
          <el-input v-model="form.contractorName" placeholder="请输入承包商名称">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectContractor"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="列入时间" prop="blacklistDate">
          <el-date-picker clearable
            v-model="form.blacklistDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择列入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="列入事由" prop="blacklistReason">
          <el-input v-model="form.blacklistReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="处罚金额（万元）" prop="penaltyAmount">
          <el-input v-model="form.penaltyAmount" placeholder="请输入处罚金额" />
        </el-form-item>
        <el-form-item label="期限（月）" prop="penaltyPeriod">
          <el-input v-model="form.penaltyPeriod" placeholder="请输入期限" />
        </el-form-item>
        <el-form-item label="解除日期" prop="releaseDate">
          <el-date-picker clearable
            v-model="form.releaseDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择解除日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="相关附件" prop="attachment">
          <file-upload v-model="form.attachment"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 承包商选择对话框 -->
    <el-dialog title="选择承包商" :visible.sync="openSelect" width="800px" append-to-body>
      <el-form :model="selectQueryParams" ref="selectQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="承包商" prop="companyName">
          <el-input
            v-model="selectQueryParams.companyName"
            placeholder="请输入承包商"
            clearable
            @keyup.enter.native="handleSelectQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSelectQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetSelectQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="selectLoading" :data="selectList" @row-dblclick="handleSelectRow">
        <el-table-column label="序号" align="center" prop="auditId" />
        <el-table-column label="审核编号" align="center" prop="auditNumber" />
        <el-table-column label="承包商" align="center" prop="companyName" />
        <el-table-column label="公司规模" align="center" prop="companySize">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.company_size" :value="scope.row.companySize"/>
          </template>
        </el-table-column>
        <el-table-column label="法定代表人" align="center" prop="legalRepresentative" />
        <el-table-column label="联系电话" align="center" prop="contactPhone" />
        <el-table-column label="经营地址" align="center" prop="businessAddress" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectRow(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="selectTotal>0"
        :total="selectTotal"
        :page.sync="selectQueryParams.pageNum"
        :limit.sync="selectQueryParams.pageSize"
        @pagination="getSelectList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelSelect">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBlacklist, getBlacklist, delBlacklist, addBlacklist, updateBlacklist } from "@/api/contents/blacklist";
import { listAudit } from "@/api/contractor/audit";

export default {
  name: "Blacklist",
  dicts: ['company_size'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 承包商黑名单表格数据
      blacklistList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示选择弹出层
      openSelect: false,
      // 列信息
      columns: [
        { key: 0, value: 'blackId', label: '序号', visible: true },
        { key: 1, value: 'contractorName', label: '承包商', visible: true },
        { key: 2, value: 'blacklistDate', label: '列入时间', visible: true },
        { key: 3, value: 'blacklistReason', label: '列入事由', visible: true },
        { key: 4, value: 'penaltyAmount', label: '处罚金额（万元）', visible: true },
        { key: 5, value: 'penaltyPeriod', label: '期限（月）', visible: true },
        { key: 6, value: 'releaseDate', label: '解除日期', visible: true },
        { key: 7, value: 'attachment', label: '相关附件', visible: true },
        { key: 8, value: 'remarks', label: '备注', visible: true },
        { key: 9, value: 'createdAt', label: '创建时间', visible: true },
        { key: 10, value: 'operation', label: '操作', visible: true }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractorName: null,
      },
      // 选择查询参数
      selectQueryParams: {
        pageNum: 1,
        pageSize: 10,
        companyName: null
      },
      // 表单参数
      form: {},
      // 选择列表数据
      selectList: [],
      // 选择总条数
      selectTotal: 0,
      // 选择加载状态
      selectLoading: false,
      // 表单校验
      rules: {
        contractorName: [
          { required: true, message: "承包商不能为空", trigger: "change" }
        ],
        blacklistDate: [
          { required: true, message: "列入时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  watch: {
    // 监听列入时间和期限月数的变化，自动计算解除日期
    'form.blacklistDate': function(newVal, oldVal) {
      this.calculateReleaseDate();
    },
    'form.penaltyPeriod': function(newVal, oldVal) {
      this.calculateReleaseDate();
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询承包商黑名单列表 */
    getList() {
      this.loading = true;
      listBlacklist(this.queryParams).then(response => {
        this.blacklistList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消选择按钮
    cancelSelect() {
      this.openSelect = false;
    },
    // 表单重置
    reset() {
      this.form = {
        blackId: null,
        auditId: null,
        contractorName: null,
        blacklistDate: null,
        blacklistReason: null,
        penaltyAmount: null,
        penaltyPeriod: null,
        releaseDate: null,
        attachment: null,
        remarks: null,
        createdBy: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 选择搜索按钮操作 */
    handleSelectQuery() {
      this.selectQueryParams.pageNum = 1;
      this.getSelectList();
    },
    /** 重置选择按钮操作 */
    resetSelectQuery() {
      this.resetForm("selectQueryForm");
      this.handleSelectQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.blackId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加承包商黑名单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const blackId = row.blackId || this.ids
      getBlacklist(blackId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改承包商黑名单";
      });
    },
    /** 选择承包商按钮操作 */
    handleSelectContractor() {
      this.selectQueryParams = {
        pageNum: 1,
        pageSize: 10,
        companyName: null
      };
      this.getSelectList();
      this.openSelect = true;
    },
    /** 查询承包商资格审查列表 */
    getSelectList() {
      this.selectLoading = true;
      listAudit(this.selectQueryParams).then(response => {
        this.selectList = response.rows;
        this.selectTotal = response.total;
        this.selectLoading = false;
      });
    },
    /** 选择承包商行 */
    handleSelectRow(row) {
      this.form.contractorName = row.companyName;
      this.openSelect = false;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.blackId != null) {
            updateBlacklist(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBlacklist(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const blackIds = row.blackId || this.ids;
      this.$modal.confirm('是否确认删除承包商黑名单编号为"' + blackIds + '"的数据项？').then(function() {
        return delBlacklist(blackIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contents/blacklist/export', {
        ...this.queryParams
      }, `blacklist_${new Date().getTime()}.xlsx`)
    },
    /** 获取文件名 */
    getFileName(filePath) {
      if (!filePath) return '';
      // 提取文件名
      const fileName = filePath.split('/').pop();
      return fileName || filePath;
    },
    /** 下载文件 */
    downloadFile(filePath) {
      // 使用resource方法下载文件
      this.$download.resource(filePath);
    },
    /** 计算解除日期 */
    calculateReleaseDate() {
      // 只有当列入时间和期限都存在时才计算
      if (this.form.blacklistDate && this.form.penaltyPeriod) {
        const blacklistDate = new Date(this.form.blacklistDate);
        // 确保期限是数字
        const penaltyPeriod = parseInt(this.form.penaltyPeriod);

        if (!isNaN(penaltyPeriod) && penaltyPeriod > 0) {
          // 创建一个新的日期对象，避免修改原始日期
          const releaseDate = new Date(blacklistDate);
          // 添加月数
          releaseDate.setMonth(releaseDate.getMonth() + penaltyPeriod);
          // 格式化为 yyyy-MM-dd
          const year = releaseDate.getFullYear();
          const month = String(releaseDate.getMonth() + 1).padStart(2, '0');
          const day = String(releaseDate.getDate()).padStart(2, '0');
          this.form.releaseDate = `${year}-${month}-${day}`;
        } else {
          // 如果期限不是有效数字，清空解除日期
          this.form.releaseDate = null;
        }
      } else {
        // 如果列入时间或期限为空，清空解除日期
        this.form.releaseDate = null;
      }
    }
  }
};
</script>
