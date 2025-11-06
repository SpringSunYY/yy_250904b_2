<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['pur:supplier:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pur:supplier:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pur:supplier:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pur:supplier:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="supplierId"/>
      <el-table-column label="供应商" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="supplierName"
      />
      <el-table-column label="地址" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="address"
      />
      <el-table-column label="经营范围" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="businessScope"
      />
      <el-table-column label="公司规模" align="center" v-if="columns[4].visible" prop="companySize">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_size" :value="scope.row.companySize"/>
        </template>
      </el-table-column>
      <el-table-column label="法定代表人" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="legalRepresen"
      />
      <el-table-column label="营业执照" align="center" v-if="columns[6].visible" prop="businessLicense" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.businessLicense" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="资质证书" align="center" v-if="columns[7].visible" prop="qualificationCertificate"
                       width="100"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.qualificationCertificate">
            <el-tooltip>
              <div slot="content">
                <div v-for="(file,index) in scope.row.qualificationCertificate.split(',')"
                     :key="index"
                     style="text-align: left;padding: 5px;"
                >
                  <el-link
                    download="getFileName(file)"
                    :href="baseUrl+file"
                    :underline="false"
                    target="_blank"
                    style="color: white;font-size: 12px"
                  >
                    <span style="cursor: pointer;"> {{ getFileName(file) }} </span>
                  </el-link>
                </div>
              </div>
              <span style="cursor: pointer; color: #409EFF;">查看文件</span>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="联系人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="contactPerson"
      />
      <el-table-column label="联系电话" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="contactPhone"
      />
      <el-table-column label="邮箱" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="email"
      />
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pur:supplier:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pur:supplier:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改供应商管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供应商" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商"/>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址"/>
        </el-form-item>
        <el-form-item label="经营范围" prop="businessScope">
          <el-input v-model="form.businessScope" placeholder="请输入经营范围"/>
        </el-form-item>
        <el-form-item label="公司规模" prop="companySize">
          <el-select v-model="form.companySize" placeholder="请选择公司规模">
            <el-option
              v-for="dict in dict.type.equip_size"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="法定代表人" prop="legalRepresen">
          <el-input v-model="form.legalRepresen" placeholder="请输入法定代表人"/>
        </el-form-item>
        <el-form-item label="营业执照" prop="businessLicense">
          <image-upload v-model="form.businessLicense"/>
        </el-form-item>
        <el-form-item label="资质证书" prop="qualificationCertificate">
          <file-upload v-model="form.qualificationCertificate"/>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱"/>
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
import { addSupplier, delSupplier, getSupplier, listSupplier, updateSupplier } from '@/api/pur/supplier'

export default {
  name: 'Supplier',
  dicts: ['equip_size'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '供应商', visible: true },
        { key: 2, label: '地址', visible: true },
        { key: 3, label: '经营范围', visible: true },
        { key: 4, label: '公司规模', visible: true },
        { key: 5, label: '法定代表人', visible: true },
        { key: 6, label: '营业执照', visible: true },
        { key: 7, label: '资质证书', visible: true },
        { key: 8, label: '联系人', visible: true },
        { key: 9, label: '联系电话', visible: true },
        { key: 10, label: '邮箱', visible: false },
        { key: 11, label: '备注', visible: false }
      ],
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
      // 供应商管理表格数据
      supplierList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierName: null,
        contactPerson: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        supplierName: [
          { required: true, message: '供应商不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //获取文件名 此功能只可以下载只有一个文件的
    getFileName(filePath) {
      if (filePath == null) {
        return
      }
      // 提取文件名或根据需求生成文件名
      return filePath.substring(filePath.lastIndexOf('/') + 1)
    },
    /** 查询供应商管理列表 */
    getList() {
      this.loading = true
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        supplierId: null,
        supplierName: null,
        address: null,
        businessScope: null,
        companySize: null,
        legalRepresen: null,
        businessLicense: null,
        qualificationCertificate: null,
        contactPerson: null,
        contactPhone: null,
        email: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.supplierId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加供应商管理'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const supplierId = row.supplierId || this.ids
      getSupplier(supplierId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改供应商管理'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.supplierId != null) {
            updateSupplier(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSupplier(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const supplierIds = row.supplierId || this.ids
      this.$modal.confirm('是否确认删除供应商管理编号为"' + supplierIds + '"的数据项？').then(function() {
        return delSupplier(supplierIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pur/supplier/export', {
        ...this.queryParams
      }, `supplier_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
