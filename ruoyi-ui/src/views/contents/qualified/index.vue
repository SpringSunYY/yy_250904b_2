<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="承包商" prop="contractorName">
        <el-input
          v-model="queryParams.contractorName"
          placeholder="请输入承包商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="承揽项目类别" prop="projectCategories">
        <el-select v-model="queryParams.projectCategories" placeholder="请选择承揽项目类别" clearable>
          <el-option
            v-for="dict in dict.type.project_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contactPerson">
        <el-input
          v-model="queryParams.contactPerson"
          placeholder="请输入联系人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['contents:qualified:add']"
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
          v-hasPermi="['contents:qualified:edit']"
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
          v-hasPermi="['contents:qualified:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contents:qualified:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qualifiedList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="qualId" v-if="columns[0].visible" />
      <el-table-column label="承包商" align="center" prop="contractorName" v-if="columns[1].visible" />
      <el-table-column label="承揽项目类别" align="center" prop="projectCategories" v-if="columns[2].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.project_type" :value="scope.row.projectCategories ? scope.row.projectCategories.split(',') : []"/>
        </template>
      </el-table-column>
      <el-table-column label="联系人" align="center" prop="contactPerson" v-if="columns[3].visible" />
      <el-table-column label="联系电话" align="center" prop="contactPhone" v-if="columns[4].visible" />
      <el-table-column label="通讯地址" align="center" prop="contactAddress" v-if="columns[5].visible" />
      <el-table-column label="备注" align="center" prop="remarks" v-if="columns[6].visible" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[7].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[8].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contents:qualified:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contents:qualified:remove']"
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

    <!-- 添加或修改合格承包商对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="承包商" prop="contractorName">
          <el-input v-model="form.contractorName" placeholder="请输入承包商">
            <el-button slot="append" icon="el-icon-search" @click="handleSelectContractor"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="承揽项目类别" prop="projectCategories">
          <el-select v-model="form.projectCategories" placeholder="请选择承揽项目类别" multiple>
            <el-option
              v-for="dict in dict.type.project_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="通讯地址" prop="contactAddress">
          <el-input v-model="form.contactAddress" type="textarea" placeholder="请输入内容" />
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
        <el-form-item label="承包商名称" prop="companyName">
          <el-input
            v-model="selectQueryParams.companyName"
            placeholder="请输入承包商名称"
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
        <el-table-column label="承包商名称" align="center" prop="companyName" />
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
import { listQualified, getQualified, delQualified, addQualified, updateQualified } from "@/api/contents/qualified";
import { listAudit } from "@/api/contractor/audit";
import { listBlacklist } from "@/api/contents/blacklist";

export default {
  name: "Qualified",
  dicts: ['project_type', 'company_size'],
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
      // 合格承包商表格数据
      qualifiedList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示选择弹出层
      openSelect: false,
      // 列信息
      columns: [
        { key: 0, value: 'qualId', label: '序号', visible: true },
        { key: 1, value: 'contractorName', label: '承包商', visible: true },
        { key: 2, value: 'projectCategories', label: '承揽项目类别', visible: true },
        { key: 3, value: 'contactPerson', label: '联系人', visible: true },
        { key: 4, value: 'contactPhone', label: '联系电话', visible: true },
        { key: 5, value: 'contactAddress', label: '通讯地址', visible: true },
        { key: 6, value: 'remarks', label: '备注', visible: true },
        { key: 7, value: 'createdAt', label: '创建时间', visible: true },
        { key: 8, value: 'operation', label: '操作', visible: true }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractorName: null,
        projectCategories: null,
        contactPerson: null,
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
          { required: true, message: "承包商不能为空", trigger: "blur" }
        ],
        projectCategories: [
          { required: true, message: "承揽项目类别不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询合格承包商列表 */
    getList() {
      this.loading = true;
      listQualified(this.queryParams).then(response => {
        this.qualifiedList = response.rows;
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
        qualId: null,
        auditId: null,
        contractorName: null,
        projectCategories: [],
        contactPerson: null,
        contactPhone: null,
        contactAddress: null,
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
      this.ids = selection.map(item => item.qualId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加合格承包商";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const qualId = row.qualId || this.ids
      getQualified(qualId).then(response => {
        this.form = response.data;
        // 处理多选项目类别
        if (this.form.projectCategories && typeof this.form.projectCategories === 'string') {
          this.form.projectCategories = this.form.projectCategories.split(',');
        } else if (!this.form.projectCategories) {
          this.form.projectCategories = [];
        }
        this.open = true;
        this.title = "修改合格承包商";
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
      // 检查该承包商是否在黑名单中且未解除
      this.checkBlacklistStatus(row.companyName).then(isBlacklisted => {
        if (isBlacklisted) {
          this.$modal.msgError("该承包商仍在黑名单中，无法选择！");
        } else {
          this.form.contractorName = row.companyName;
          this.form.contactPerson = row.legalRepresentative;
          this.form.contactPhone = row.contactPhone;
          this.form.contactAddress = row.businessAddress;
          this.form.auditId = row.auditId;
          this.openSelect = false;
        }
      });
    },
    /** 检查承包商黑名单状态 */
    checkBlacklistStatus(contractorName) {
      return new Promise((resolve, reject) => {
        // 查询该承包商在黑名单中的记录
        listBlacklist({ contractorName: contractorName }).then(response => {
          const blacklistRecords = response.rows;
          const today = new Date();
          today.setHours(0, 0, 0, 0); // 将时间部分置为0，只比较日期部分

          // 检查是否有未解除的黑名单记录
          for (let record of blacklistRecords) {
            // 如果解除日期为空，则说明仍在黑名单中
            if (!record.releaseDate) {
              resolve(true); // 仍在黑名单中
              return;
            }

            // 如果解除日期大于今天，则说明仍在黑名单中
            const releaseDate = new Date(record.releaseDate);
            releaseDate.setHours(0, 0, 0, 0); // 将时间部分置为0，只比较日期部分

            if (releaseDate >= today) {
              resolve(true); // 仍在黑名单中
              return;
            }
          }

          resolve(false); // 不在黑名单中或者黑名单已解除
        }).catch(error => {
          console.error("检查黑名单状态出错:", error);
          // 出错时允许选择，避免影响正常操作
          resolve(false);
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理多选项目类别
          const form_data = Object.assign({}, this.form);
          if (Array.isArray(form_data.projectCategories)) {
            form_data.projectCategories = form_data.projectCategories.join(',');
          }

          if (this.form.qualId != null) {
            updateQualified(form_data).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQualified(form_data).then(response => {
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
      const qualIds = row.qualId || this.ids;
      this.$modal.confirm('是否确认删除合格承包商编号为"' + qualIds + '"的数据项？').then(function() {
        return delQualified(qualIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contents/qualified/export', {
        ...this.queryParams
      }, `qualified_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
