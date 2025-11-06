<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类别" prop="equipmentCategory">
        <el-select v-model="queryParams.equipmentCategory" placeholder="请选择设备类别" clearable>
          <el-option
            v-for="dict in dict.type.equipment_special_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="安装型式" prop="installationType">
        <el-select v-model="queryParams.installationType" placeholder="请选择安装型式" clearable>
          <el-option
            v-for="dict in dict.type.installation_type"
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
          v-hasPermi="['special:vessel:add']"
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
          v-hasPermi="['special:vessel:edit']"
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
          v-hasPermi="['special:vessel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['special:vessel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="vesselList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
        <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="name" />
        <el-table-column label="规格" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="specification" />
        <el-table-column label="容积(m³)" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="volume" />
        <el-table-column label="设备类别" align="center" v-if="columns[4].visible" prop="equipmentCategory">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equipment_special_category" :value="scope.row.equipmentCategory"/>
        </template>
      </el-table-column>
        <el-table-column label="安装型式" align="center" v-if="columns[5].visible" prop="installationType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.installation_type" :value="scope.row.installationType"/>
        </template>
      </el-table-column>
        <el-table-column label="设计压力(MPa)" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="designPressure" />
        <el-table-column label="设计温度(℃)" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="designTemperature" />
        <el-table-column label="工作压力(MPa)" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="workingPressure" />
        <el-table-column label="工作温度(℃)" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible" prop="workingTemperature" />
        <el-table-column label="材质" align="center" v-if="columns[10].visible" prop="material">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_material" :value="scope.row.material"/>
        </template>
      </el-table-column>
        <el-table-column label="介质" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="medium" />
        <el-table-column label="容器净重(KG)" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="netWeight" />
        <el-table-column label="生产厂家" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible" prop="manufacturer" />
        <el-table-column label="制造日期" align="center" v-if="columns[14].visible" prop="manufactureDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.manufactureDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="制造许可证号" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible" prop="manufactureLicenseNo" />
        <el-table-column label="制造编号" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible" prop="manufactureSerialNo" />
        <el-table-column label="安装单位" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible" prop="installationUnit" />
        <el-table-column label="安装日期" align="center" v-if="columns[18].visible" prop="installationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="投入使用日期" align="center" v-if="columns[19].visible" prop="commissioningDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.commissioningDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="设备代码" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible" prop="equipmentCode" />
        <el-table-column label="使用证编号" :show-overflow-tooltip="true" align="center" v-if="columns[21].visible" prop="usageCertificateNo" />
        <el-table-column label="使用登记证文本" align="center" v-if="columns[22].visible" prop="usageRegistrationCertificate" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.usageRegistrationCertificate" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[23].visible" prop="remarks" />
        <el-table-column label="创建时间" align="center" v-if="columns[24].visible" prop="createdTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['special:vessel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['special:vessel:remove']"
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

    <!-- 添加或修改压力容器对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="name">
          <el-row :gutter="10">
            <el-col :span="18">
              <el-input v-model="form.name" placeholder="请输入设备名称" />
            </el-col>
            <el-col :span="6">
              <el-button type="primary" @click="openEquipSelector">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="容积(m³)" prop="volume">
          <el-input v-model="form.volume" placeholder="请输入容积(m³)" />
        </el-form-item>
        <el-form-item label="设备类别" prop="equipmentCategory">
          <el-select v-model="form.equipmentCategory" placeholder="请选择设备类别">
            <el-option
              v-for="dict in dict.type.equipment_special_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="安装型式" prop="installationType">
          <el-select v-model="form.installationType" placeholder="请选择安装型式">
            <el-option
              v-for="dict in dict.type.installation_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设计压力(MPa)" prop="designPressure">
          <el-input v-model="form.designPressure" placeholder="请输入设计压力(MPa)" />
        </el-form-item>
        <el-form-item label="设计温度(℃)" prop="designTemperature">
          <el-input v-model="form.designTemperature" placeholder="请输入设计温度(℃)" />
        </el-form-item>
        <el-form-item label="工作压力(MPa)" prop="workingPressure">
          <el-input v-model="form.workingPressure" placeholder="请输入工作压力(MPa)" />
        </el-form-item>
        <el-form-item label="工作温度(℃)" prop="workingTemperature">
          <el-input v-model="form.workingTemperature" placeholder="请输入工作温度(℃)" />
        </el-form-item>
        <el-form-item label="材质" prop="material">
          <el-select v-model="form.material" placeholder="请选择材质">
            <el-option
              v-for="dict in dict.type.special_material"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="介质" prop="medium">
          <el-input v-model="form.medium" placeholder="请输入介质" />
        </el-form-item>
        <el-form-item label="容器净重(KG)" prop="netWeight">
          <el-input v-model="form.netWeight" placeholder="请输入容器净重(KG)" />
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入生产厂家" />
        </el-form-item>
        <el-form-item label="制造日期" prop="manufactureDate">
          <el-date-picker clearable
            v-model="form.manufactureDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择制造日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="制造许可证号" prop="manufactureLicenseNo">
          <el-input v-model="form.manufactureLicenseNo" placeholder="请输入制造许可证号" />
        </el-form-item>
        <el-form-item label="制造编号" prop="manufactureSerialNo">
          <el-input v-model="form.manufactureSerialNo" placeholder="请输入制造编号" />
        </el-form-item>
        <el-form-item label="安装单位" prop="installationUnit">
          <el-input v-model="form.installationUnit" placeholder="请输入安装单位" />
        </el-form-item>
        <el-form-item label="安装日期" prop="installationDate">
          <el-date-picker clearable
            v-model="form.installationDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择安装日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="投入使用日期" prop="commissioningDate">
          <el-date-picker clearable
            v-model="form.commissioningDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择投入使用日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="设备代码" prop="equipmentCode">
          <el-input v-model="form.equipmentCode" placeholder="请输入设备代码" />
        </el-form-item>
        <el-form-item label="使用证编号" prop="usageCertificateNo">
          <el-input v-model="form.usageCertificateNo" placeholder="请输入使用证编号" />
        </el-form-item>
        <el-form-item label="使用登记证文本" prop="usageRegistrationCertificate">
          <image-upload v-model="form.usageRegistrationCertificate"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设备选择对话框 -->
    <el-dialog :title="equipSelectorTitle" :visible.sync="equipSelectorOpen" width="900px" append-to-body>
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
        <el-table-column label="设备名称" align="center" prop="equName" />
        <el-table-column label="设备位号" align="center" prop="equCode" />
        <el-table-column label="设备类型" align="center" prop="type">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.equip_type" :value="scope.row.type"/>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" align="center" prop="specification" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectEquip(scope.row)"
            >选择</el-button>
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
  import { getVessel, listVessel, addVessel, updateVessel, delVessel } from "@/api/special/vessel";
  import { listLedger } from "@/api/equip/ledger";

  export default {
  name: "Vessel",
  dicts: ['equipment_special_category', 'special_material', 'installation_type', 'equip_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
          { key: 1, label: '设备名称', visible: true },
          { key: 2, label: '规格', visible: true },
          { key: 3, label: '容积(m³)', visible: true },
          { key: 4, label: '设备类别', visible: true },
          { key: 5, label: '安装型式', visible: true },
          { key: 6, label: '设计压力(MPa)', visible: true },
          { key: 7, label: '设计温度(℃)', visible: true },
          { key: 8, label: '工作压力(MPa)', visible: true },
          { key: 9, label: '工作温度(℃)', visible: true },
          { key: 10, label: '材质', visible: true },
          { key: 11, label: '介质', visible: true },
          { key: 12, label: '容器净重(KG)', visible: true },
          { key: 13, label: '生产厂家', visible: true },
          { key: 14, label: '制造日期', visible: true },
          { key: 15, label: '制造许可证号', visible: true },
          { key: 16, label: '制造编号', visible: true },
          { key: 17, label: '安装单位', visible: true },
          { key: 18, label: '安装日期', visible: true },
          { key: 19, label: '投入使用日期', visible: true },
          { key: 20, label: '设备代码', visible: true },
          { key: 21, label: '使用证编号', visible: true },
          { key: 22, label: '使用登记证文本', visible: true },
          { key: 23, label: '备注', visible: true },
          { key: 24, label: '创建时间', visible: true },
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
      // 压力容器表格数据
      vesselList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示设备选择弹出层
      equipSelectorOpen: false,
      // 设备选择弹出层标题
      equipSelectorTitle: "选择设备",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        equipmentCategory: null,
        installationType: null,
      },
      // 设备查询参数
      equipQueryParams: {
        pageNum: 1,
        pageSize: 10,
        equName: undefined,
        equCode: undefined,
        specialEquipment: "Y" // 只查询特种设备
      },
      // 设备列表
      equipListSelector: [],
      // 设备总数
      equipTotal: 0,
      // 设备加载
      equipLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询压力容器列表 */
    getList() {
      this.loading = true;
      listVessel(this.queryParams).then(response => {
        this.vesselList = response.rows;
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
        name: null,
        specification: null,
        volume: null,
        equipmentCategory: null,
        installationType: null,
        designPressure: null,
        designTemperature: null,
        workingPressure: null,
        workingTemperature: null,
        material: null,
        medium: null,
        netWeight: null,
        manufacturer: null,
        manufactureDate: null,
        manufactureLicenseNo: null,
        manufactureSerialNo: null,
        installationUnit: null,
        installationDate: null,
        commissioningDate: null,
        equipmentCode: null,
        usageCertificateNo: null,
        usageRegistrationCertificate: null,
        remarks: null,
        createdBy: null,
        createdTime: null,
        updatedBy: null,
        updatedTime: null
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
      this.title = "添加压力容器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getVessel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改压力容器";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVessel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).catch(error => {
              console.error("修改失败:", error);
              this.$modal.msgError("修改失败");
            });
          } else {
            addVessel(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).catch(error => {
              console.error("新增失败:", error);
              this.$modal.msgError("新增失败");
            });
          }
        } else {
          this.$modal.msgError("表单校验未通过，请检查输入信息");
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除压力容器编号为"' + ids + '"的数据项？').then(function() {
        return delVessel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('special/vessel/export', {
        ...this.queryParams
      }, `vessel_${new Date().getTime()}.xlsx`)
    },
    /** 打开设备选择器 */
    openEquipSelector() {
      this.equipSelectorOpen = true;
      this.equipSelectorTitle = "选择设备";
      this.getEquipList();
    },
    /** 查询设备列表 */
    getEquipList() {
      this.equipLoading = true;
      listLedger(this.equipQueryParams).then(response => {
        this.equipListSelector = response.rows;
        this.equipTotal = response.total;
        this.equipLoading = false;
      });
    },
    /** 设备搜索按钮操作 */
    handleEquipQuery() {
      this.equipQueryParams.pageNum = 1;
      this.getEquipList();
    },
    /** 设备重置按钮操作 */
    resetEquipQuery() {
      this.resetForm("equipQueryForm");
      this.handleEquipQuery();
    },
    /** 选择设备 */
    handleSelectEquip(row) {
      this.form.name = row.equName;
      this.form.specification = row.specification;
      this.equipSelectorOpen = false;
    }
  }
};
</script>
