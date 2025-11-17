<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="personnelName">
        <el-input
          v-model="queryParams.personnelName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职业资格类型" prop="vqType">
        <el-select v-model="queryParams.vqType" placeholder="请选择职业资格类型" clearable>
          <el-option
            v-for="dict in dict.type.vq_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="证书状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择证书状态" clearable>
          <el-option
            v-for="dict in dict.type.contractor_certificate_status"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['personnel:operations:add']"
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
          v-hasPermi="['personnel:operations:edit']"
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
          v-hasPermi="['personnel:operations:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['personnel:operations:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="operationsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" v-if="columns[0].visible" />
      <el-table-column label="姓名" align="center" prop="personnelName" v-if="columns[1].visible" />
      <el-table-column label="职业资格类型" align="center" prop="vqType" v-if="columns[2].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.vq_type" :value="scope.row.vqType"/>
        </template>
      </el-table-column>
      <el-table-column label="证书名称" align="center" prop="certificate" v-if="columns[3].visible" />
      <el-table-column label="证书编号" align="center" prop="certificateNumber" v-if="columns[4].visible" />
      <el-table-column label="发证机关" align="center" prop="issueOrganization" v-if="columns[5].visible" />
      <el-table-column label="发证日期" align="center" prop="issueDate" width="180" v-if="columns[6].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.issueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="证书有效期至" align="center" prop="expiryDate" width="180" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="证书状态" align="center" prop="status" v-if="columns[8].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_certificate_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="下次培训日期" align="center" prop="nextTrainingDate" width="180" v-if="columns[9].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextTrainingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="证书扫描件" align="center" prop="certificateScan" width="100" v-if="columns[10].visible">
        <template slot-scope="scope">
          <image-preview :src="scope.row.certificateScan" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[11].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[12].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[13].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['personnel:operations:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['personnel:operations:remove']"
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

    <!-- 添加或修改特种人员台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="personnelId">
          <el-input v-model="form.personnelName" placeholder="请输入姓名">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectPerson"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="职业资格类型" prop="vqType">
          <el-select v-model="form.vqType" placeholder="请选择职业资格类型">
            <el-option
              v-for="dict in dict.type.vq_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证书名称" prop="certificate">
          <el-input v-model="form.certificate" placeholder="请输入证书名称" />
        </el-form-item>
        <el-form-item label="证书编号" prop="certificateNumber">
          <el-input v-model="form.certificateNumber" placeholder="请输入证书编号" />
        </el-form-item>
        <el-form-item label="发证机关" prop="issueOrganization">
          <el-input v-model="form.issueOrganization" placeholder="请输入发证机关" />
        </el-form-item>
        <el-form-item label="发证日期" prop="issueDate">
          <el-date-picker clearable
            v-model="form.issueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发证日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="证书有效期至" prop="expiryDate">
          <el-date-picker clearable
            v-model="form.expiryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择证书有效期至">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="证书状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择证书状态">
            <el-option
              v-for="dict in dict.type.contractor_certificate_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下次培训日期" prop="nextTrainingDate">
          <el-date-picker clearable
            v-model="form.nextTrainingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择下次培训日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="证书扫描件" prop="certificateScan">
          <image-upload v-model="form.certificateScan"/>
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

    <!-- 人员选择对话框 -->
    <el-dialog title="选择人员" :visible.sync="personSelectOpen" width="1100px" append-to-body>
      <el-form :model="personQueryParams" ref="personQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="姓名">
          <el-input
            v-model="personQueryParams.personnelName"
            placeholder="请输入姓名"
            clearable
            @keyup.enter.native="handlePersonQuery"
          />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input
            v-model="personQueryParams.idCardNumber"
            placeholder="请输入身份证号"
            clearable
            @keyup.enter.native="handlePersonQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handlePersonQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetPersonQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="personLoading" :data="personList" @row-dblclick="handleSelectPersonRow">
        <el-table-column label="姓名" align="center" prop="personnelName" />
        <el-table-column label="身份证号" align="center" prop="idCardNumber" />
        <el-table-column label="性别" align="center" prop="gender" />
        <el-table-column label="工种" align="center" prop="workType" />
        <el-table-column label="联系方式" align="center" prop="contactPhone" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectPersonRow(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="personTotal>0"
        :total="personTotal"
        :page.sync="personQueryParams.pageNum"
        :limit.sync="personQueryParams.pageSize"
        @pagination="getPersonList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="personSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listOperations, getOperations, delOperations, addOperations, updateOperations } from "@/api/personnel/operations";
import { listRegister } from "@/api/personnel/register";

export default {
  name: "Operations",
  dicts: ['contractor_certificate_status', 'vq_type'],
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
      // 特种人员台账表格数据
      operationsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 人员选择对话框标题
      personDialogTitle: "选择人员",
      // 是否显示人员选择弹出层
      personSelectOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        personnelName: null,
        vqType: null,
        status: null,
      },
      // 人员查询参数
      personQueryParams: {
        pageNum: 1,
        pageSize: 10,
        personnelName: null,
        idCardNumber: null
      },
      // 人员总条数
      personTotal: 0,
      // 人员表格数据
      personList: [],
      // 人员加载状态
      personLoading: false,
      // 表单参数
      form: {},
      // 列信息
      columns: [
        { key: 0, label: `序号`, visible: true },
        { key: 1, label: `姓名`, visible: true },
        { key: 2, label: `职业资格类型`, visible: true },
        { key: 3, label: `证书名称`, visible: true },
        { key: 4, label: `证书编号`, visible: true },
        { key: 5, label: `发证机关`, visible: true },
        { key: 6, label: `发证日期`, visible: true },
        { key: 7, label: `证书有效期至`, visible: true },
        { key: 8, label: `证书状态`, visible: true },
        { key: 9, label: `下次培训日期`, visible: true },
        { key: 10, label: `证书扫描件`, visible: true },
        { key: 11, label: `备注`, visible: true },
        { key: 12, label: `创建时间`, visible: true },
        { key: 13, label: `操作`, visible: true }
      ],
      // 表单校验
      rules: {
        personnelName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        vqType: [
          { required: true, message: "职业资格类型不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询特种人员台账列表 */
    getList() {
      this.loading = true;
      listOperations(this.queryParams).then(response => {
        this.operationsList = response.rows;
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
        id: null,
        personnelId: null,
        personnelName: null,
        vqType: null,
        certificate: null,
        certificateNumber: null,
        issueOrganization: null,
        issueDate: null,
        expiryDate: null,
        status: null,
        nextTrainingDate: null,
        certificateScan: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加特种人员台账";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOperations(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改特种人员台账";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOperations(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOperations(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除特种人员台账编号为"' + ids + '"的数据项？').then(function() {
        return delOperations(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('personnel/operations/export', {
        ...this.queryParams
      }, `operations_${new Date().getTime()}.xlsx`)
    },
    /** 选择人员按钮操作 */
    handleSelectPerson() {
      this.personSelectOpen = true;
      this.getPersonList();
    },
    /** 查询人员信息台账列表 */
    getPersonList() {
      this.personLoading = true;
      listRegister(this.personQueryParams).then(response => {
        this.personList = response.rows;
        this.personTotal = response.total;
        this.personLoading = false;
      });
    },
    /** 人员搜索按钮操作 */
    handlePersonQuery() {
      this.personQueryParams.pageNum = 1;
      this.getPersonList();
    },
    /** 重置人员搜索按钮操作 */
    resetPersonQuery() {
      this.resetForm("personQueryForm");
      this.handlePersonQuery();
    },
    /** 选择人员 */
    handleSelectPersonRow(row) {
      this.form.personnelId = row.personnelId;
      this.form.personnelName = row.personnelName;
      this.personSelectOpen = false;
    }
  }
};
</script>
