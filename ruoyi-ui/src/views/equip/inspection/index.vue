<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检验类型" prop="inspectionType">
        <el-select v-model="queryParams.inspectionType" placeholder="请选择检验类型" clearable>
          <el-option
            v-for="dict in dict.type.equip_inspection_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检验周期" prop="inspectionCycle">
        <el-select v-model="queryParams.inspectionCycle" placeholder="请选择检验周期" clearable>
          <el-option
            v-for="dict in dict.type.equip_inspection_cycle"
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
          v-hasPermi="['equip:inspection:add']"
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
          v-hasPermi="['equip:inspection:edit']"
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
          v-hasPermi="['equip:inspection:remove']"
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
          v-hasPermi="['equip:inspection:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inspectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="inspectionId" />
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="equipmentName" />
      <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="equipmentCode" />
      <el-table-column label="检验类型" align="center" v-if="columns[3].visible" prop="inspectionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspection_type" :value="scope.row.inspectionType"/>
        </template>
      </el-table-column>
      <el-table-column label="检验周期" align="center" v-if="columns[4].visible" prop="inspectionCycle">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspection_cycle" :value="scope.row.inspectionCycle"/>
        </template>
      </el-table-column>
      <el-table-column label="检验日期" align="center" v-if="columns[5].visible" prop="actualDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检验机构" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="inspectionOrg" />
      <el-table-column label="检验结论" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="inspectionStatus" />
      <el-table-column label="检验证书编号" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="certificateNo" />
      <el-table-column label="检验报告文件" align="center" v-if="columns[9].visible" prop="reportFile" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.reportFile">
            <el-tooltip placement="top">
              <div slot="content">
                <div v-for="(file,index) in scope.row.reportFile.split(',')"
                     :key="index"
                     style="text-align: left;padding: 5px;">
                  <el-link
                    :download="getFileName(file)"
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
      <el-table-column label="证书有效期至" align="center" v-if="columns[10].visible" prop="validDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.validDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['equip:inspection:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equip:inspection:remove']"
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

    <!-- 添加或修改设备检验检测对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="设备" prop="equipmentId">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.equipmentId"
                placeholder="请选择设备"
                @change="handleEquipChange"
                style="width: 100%"
              >
                <el-option
                  v-for="item in equipList"
                  :key="item.equipId"
                  :label="item.equName"
                  :value="item.equipId"
                >
                  <span style="float: left">{{ item.equName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.equCode }}</span>
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="openEquipSelector" style="margin-left: 10px;">选择设备</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="设备位号" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="设备位号" disabled/>
        </el-form-item>
        <el-form-item label="检验类型" prop="inspectionType">
          <el-select v-model="form.inspectionType" placeholder="请选择检验类型">
            <el-option
              v-for="dict in dict.type.equip_inspection_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检验周期" prop="inspectionCycle">
          <el-select v-model="form.inspectionCycle" placeholder="请选择检验周期">
            <el-option
              v-for="dict in dict.type.equip_inspection_cycle"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="检验日期" prop="actualDate">
          <el-date-picker clearable
                          v-model="form.actualDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择检验日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检验机构" prop="inspectionOrg">
          <el-input v-model="form.inspectionOrg" placeholder="请输入检验机构"/>
        </el-form-item>
        <el-form-item label="检验结论" prop="inspectionStatus">
          <el-input v-model="form.inspectionStatus" placeholder="请输入检验结论"/>
        </el-form-item>
        <el-form-item label="证书编号" prop="certificateNo">
          <el-input v-model="form.certificateNo" placeholder="请输入证书编号"/>
        </el-form-item>
        <el-form-item label="检验报告" prop="reportFile">
          <file-upload v-model="form.reportFile"/>
        </el-form-item>
        <el-form-item label="证书有效期至" prop="validDate">
          <el-date-picker clearable
                          v-model="form.validDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择证书有效期至"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设备选择对话框 -->
    <el-dialog title="选择设备" :visible.sync="equipSelectorOpen" width="800px" append-to-body>
      <el-form :model="equipQueryParams" ref="equipQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="设备名称">
          <el-input
            v-model="equipQueryParams.equName"
            placeholder="请输入设备名称"
            clearable
            @keyup.enter.native="handleEquipQuery"
          />
        </el-form-item>
        <el-form-item label="设备位号">
          <el-input
            v-model="equipQueryParams.equCode"
            placeholder="请输入设备位号"
            clearable
            @keyup.enter.native="handleEquipQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleEquipQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetEquipQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="equipLoading"
        :data="equipListSelector"
        @row-dblclick="handleSelectEquip"
        max-height="400"
      >
        <el-table-column label="设备名称" align="center" prop="equName"/>
        <el-table-column label="设备位号" align="center" prop="equCode"/>
        <el-table-column label="设备类型" align="center" prop="type">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_type" :value="scope.row.type"/>
          </template>
        </el-table-column>
        <el-table-column label="管理级别" align="center" prop="equipLevels">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_levels" :value="scope.row.equipLevels"/>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" align="center" prop="specification"/>
        <el-table-column label="安装位置" align="center" prop="installLocation">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_location" :value="scope.row.installLocation"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectEquip(scope.row)"
            >选择
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="equipTotal>0"
        :total="equipTotal"
        :page.sync="equipQueryParams.pageNum"
        :limit.sync="equipQueryParams.pageSize"
        @pagination="getEquipList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="equipSelectorOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInspection, getInspection, delInspection, addInspection, updateInspection } from '@/api/equip/inspection'
import { listLedger } from '@/api/equip/ledger'

export default {
  name: 'Inspection',
  dicts: ['equip_inspection_type', 'equip_ledger', 'equip_type', 'equip_levels', 'equip_location', 'equip_inspection_cycle'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '设备名称', visible: true },
        { key: 2, label: '设备位号', visible: true },
        { key: 3, label: '检验类型', visible: true },
        { key: 4, label: '检验周期', visible: true },
        { key: 5, label: '检验日期', visible: true },
        { key: 6, label: '检验机构', visible: true },
        { key: 7, label: '检验结论', visible: true },
        { key: 8, label: '检验证书编号', visible: true },
        { key: 9, label: '检验报告文件', visible: true },
        { key: 10, label: '证书有效期至', visible: true },
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
      // 设备检验检测表格数据
      inspectionList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示设备选择弹出层
      equipSelectorOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: null,
        inspectionType: null,
        inspectionCycle: null
      },
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined
      },
      // 设备列表
      equipList: [],
      // 设备选择器列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载
      equipLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        equipmentId: [
          { required: true, message: '设备不能为空', trigger: 'change' }
        ],
        inspectionType: [
          { required: true, message: '检验类型不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getEquipList()
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
    /** 查询设备检验检测列表 */
    getList() {
      this.loading = true
      listInspection(this.queryParams).then(response => {
        this.inspectionList = response.rows
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
        inspectionId: null,
        equipmentId: null,
        equipmentName: null,
        equipmentCode: null,
        inspectionType: null,
        inspectionCycle: null,
        actualDate: null,
        inspectionOrg: null,
        inspectionStatus: null,
        certificateNo: null,
        reportFile: null,
        validDate: null,
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
      this.ids = selection.map(item => item.inspectionId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备检验检测'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const inspectionId = row.inspectionId || this.ids
      getInspection(inspectionId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改设备检验检测'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.inspectionId != null) {
            updateInspection(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addInspection(this.form).then(response => {
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
      const inspectionIds = row.inspectionId || this.ids
      this.$modal.confirm('是否确认删除设备检验检测编号为"' + inspectionIds + '"的数据项？').then(function() {
        return delInspection(inspectionIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('equip/inspection/export', {
        ...this.queryParams
      }, `inspection_${new Date().getTime()}.xlsx`)
    },
    /** 获取设备列表 */
    getEquipList() {
      this.equipLoading = true
      listLedger(this.equipQueryParams).then(response => {
        this.equipListSelector = response.rows
        this.equipTotal = response.total
        this.equipLoading = false

        // 同时更新下拉框中的设备列表
        if (!this.equipQueryParams.equName && !this.equipQueryParams.equCode) {
          this.equipList = response.rows
        }
      })
    },
    /** 设备搜索 */
    handleEquipQuery() {
      this.equipQueryParams.pageNum = 1
      this.getEquipList()
    },
    /** 重置设备搜索 */
    resetEquipQuery() {
      this.resetForm('equipQueryForm')
      this.handleEquipQuery()
    },
    /** 选择设备 */
    handleSelectEquip(row) {
      this.form.equipmentId = row.equipId
      this.form.equipmentName = row.equName
      this.form.equipmentCode = row.equCode
      this.equipSelectorOpen = false
    },
    /** 打开设备选择器 */
    openEquipSelector() {
      this.equipSelectorOpen = true
      this.resetEquipQuery()
    },
    /** 设备变更 */
    handleEquipChange(val) {
      const selectedEquip = this.equipList.find(item => item.equipId === val)
      if (selectedEquip) {
        this.form.equipmentName = selectedEquip.equName
        this.form.equipmentCode = selectedEquip.equCode
      }
    }
  }
}
</script>
