<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="制度名称" prop="systemName">
        <el-input
          v-model="queryParams.systemName"
          placeholder="请输入制度名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="managementStatus">
        <el-select v-model="queryParams.managementStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.management_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
       t   ype="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['contractor:management:add']"
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
          v-hasPermi="['contractor:management:edit']"
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
          v-hasPermi="['contractor:management:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contractor:management:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="managementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="nanageId" v-if="columns[0].visible" />
      <el-table-column label="制度编号" align="center" prop="systemCode" v-if="columns[1].visible" />
      <el-table-column label="制度名称" align="center" prop="systemName" v-if="columns[2].visible" />
      <el-table-column label="版本号" align="center" prop="version" v-if="columns[3].visible" />
      <el-table-column label="发布日期" align="center" prop="publishDate" width="180" v-if="columns[4].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生效日期" align="center" prop="effectiveDate" width="180" v-if="columns[5].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修订日期" align="center" prop="revisionDate" width="180" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.revisionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="变更说明" align="center" prop="changeDescription" v-if="columns[7].visible" />
      <el-table-column label="状态" align="center" prop="managementStatus" v-if="columns[8].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.management_status" :value="scope.row.managementStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="责任部门" align="center" prop="responsibleDepartment" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ scope.row.responsibleDepartment ? scope.row.responsibleDepartment.split('/').pop() : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="制度文本" align="center" prop="systemContent" v-if="columns[10].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.systemContent && scope.row.systemContent.length > 0">
            <div v-for="(file, index) in scope.row.systemContent.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[11].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[12].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[13].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contractor:management:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contractor:management:remove']"
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

    <!-- 添加或修改承包商制度对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="制度编号" prop="systemCode">
          <el-input v-model="form.systemCode" placeholder="请输入制度编号" />
        </el-form-item>
        <el-form-item label="制度名称" prop="systemName">
          <el-input v-model="form.systemName" placeholder="请输入制度名称" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="发布日期" prop="publishDate">
          <el-date-picker clearable
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发布日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生效日期" prop="effectiveDate">
          <el-date-picker clearable
            v-model="form.effectiveDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择生效日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="修订日期" prop="revisionDate">
          <el-date-picker clearable
            v-model="form.revisionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择修订日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="变更说明" prop="changeDescription">
          <el-input v-model="form.changeDescription" placeholder="请输入变更说明" />
        </el-form-item>
        <el-form-item label="状态" prop="managementStatus">
          <el-select v-model="form.managementStatus" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.management_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="责任部门"prop="responsibleDepartment">
          <el-cascader
            v-model="form.responsibleDepartment"
            :options="deptOptions"
            :props="deptProps"
            placeholder="请选择责任部门"
            clearable
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="制度文本" prop="systemContent">
          <file-upload v-model="form.systemContent"/>
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
  </div>
</template>

<script>
import { listManagement, getManagement, delManagement, addManagement, updateManagement } from "@/api/contractor/management";
import { treeselect } from "@/api/system/dept";

export default {
  name: "Management",
  dicts: ['management_status'],
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
      // 承包商制度表格数据
      managementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 列信息
      columns: [
        { key: 0, value: 'nanageId', label: '序号', visible: true },
        { key: 1, value: 'systemCode', label: '制度编号', visible: true },
        { key: 2, value: 'systemName', label: '制度名称', visible: true },
        { key: 3, value: 'version', label: '版本号', visible: true },
        { key: 4, value: 'publishDate', label: '发布日期', visible: true },
        { key: 5, value: 'effectiveDate', label: '生效日期', visible: true },
        { key: 6, value: 'revisionDate', label: '修订日期', visible: true },
        { key: 7, value: 'changeDescription', label: '变更说明', visible: true },
        { key: 8, value: 'managementStatus', label: '状态', visible: true },
        { key: 9, value: 'responsibleDepartment', label: '责任部门', visible: true },
        { key: 10, value: 'systemContent', label: '制度文本', visible: true },
        { key: 11, value: 'createdAt', label: '创建时间', visible: true },
        { key: 12, value: 'remarks', label: '备注', visible: true },
        { key: 13, value: 'operation', label: '操作', visible: true }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        systemName: null,
        managementStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        systemName: [
          { required: true, message: "制度名称不能为空", trigger: "blur" }
        ],
      },
      // 部门树选项
      deptOptions: [],
      // 部门级联属性
      deptProps: {
        value: "label",
        label: "label",
        children: "children",
        expandTrigger: "hover"
      },
    };
  },
  async created() {
    await this.getList();
    await this.getDeptTree();
  },
  methods: {
    /** 查询承包商制度列表 */
    getList() {
      this.loading = true;
      listManagement(this.queryParams).then(response => {
        this.managementList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        nanageId: null,
        systemCode: null,
        systemName: null,
        version: null,
        publishDate: null,
        effectiveDate: null,
        revisionDate: null,
        changeDescription: null,
        managementStatus: null,
        responsibleDepartment: null,
        systemContent: null,
        createdBy: null,
        createdAt: null,
        remarks: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.nanageId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加承包商制度";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const nanageId = row.nanageId || this.ids
      getManagement(nanageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改承包商制度";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 创建提交数据的副本
          const submitData = Object.assign({}, this.form);

          // 如果responsibleDepartment是数组，则转换为字符串
          if (Array.isArray(submitData.responsibleDepartment)) {
            submitData.responsibleDepartment = submitData.responsibleDepartment.join('/');
          }

          if (this.form.nanageId != null) {
            updateManagement(submitData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManagement(submitData).then(response => {
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
      const nanageIds = row.nanageId || this.ids;
      this.$modal.confirm('是否确认删除承包商制度编号为"' + nanageIds + '"的数据项？').then(function() {
        return delManagement(nanageIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 查询部门下拉树 */
    async getDeptTree() {
      try {
        const response = await treeselect();
        this.deptOptions = response.data;
      } catch (error) {
        console.error('获取部门树失败:', error);
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contractor/management/export', {
        ...this.queryParams
      }, `management_${new Date().getTime()}.xlsx`)
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
    }
  }
};
</script>
