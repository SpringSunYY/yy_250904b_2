<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位内编号" prop="internalNumber">
        <el-input
          v-model="queryParams.internalNumber"
          placeholder="请输入单位内编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="deviceType">
        <el-select v-model="queryParams.deviceType" placeholder="请选择设备类型" clearable>
          <el-option
            v-for="dict in dict.type.special_device_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="动力方式" prop="powerMethod">
        <el-select v-model="queryParams.powerMethod" placeholder="请选择动力方式" clearable>
          <el-option
            v-for="dict in dict.type.power_method"
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
          v-hasPermi="['special:inventory:add']"
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
          v-hasPermi="['special:inventory:edit']"
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
          v-hasPermi="['special:inventory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['special:inventory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id" />
      <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="deviceName" />
      <el-table-column label="单位内编号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="internalNumber" />
      <el-table-column label="设备类型" align="center" v-if="columns[3].visible" prop="deviceType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_device_type" :value="scope.row.deviceType"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="specificationModel" />
      <el-table-column label="动力方式" align="center" v-if="columns[5].visible" prop="powerMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.power_method" :value="scope.row.powerMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="制造单位" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="manufacturer" />
      <el-table-column label="制造日期" align="center" v-if="columns[7].visible" prop="manufactureDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.manufactureDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出厂编号" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="serialNumber" />
      <el-table-column label="投入使用日期" align="center" v-if="columns[9].visible" prop="useDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.useDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="额定载荷" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible" prop="ratedLoad" />
      <el-table-column label="设备(注册)代码" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="deviceCode" />
      <el-table-column label="登记机关" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="registrationAuthority" />
      <el-table-column label="登记证编号" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible" prop="registrationNumber" />
      <el-table-column label="登记证图片" align="center" v-if="columns[14].visible" prop="registrationCertificate" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.registrationCertificate" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[15].visible" prop="remarks" />
      <el-table-column label="创建时间" align="center" v-if="columns[16].visible" prop="createdTime" width="180">
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
            v-hasPermi="['special:inventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['special:inventory:remove']"
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

    <!-- 添加或修改场(厂)内专用机动车辆对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="deviceName">
          <el-row :gutter="10">
            <el-col :span="18">
              <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
            </el-col>
            <el-col :span="6">
              <el-button type="primary" @click="openEquipSelector">选择</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="单位内编号" prop="internalNumber">
          <el-input v-model="form.internalNumber" placeholder="请输入单位内编号" />
        </el-form-item>
        <el-form-item label="设备类型" prop="deviceType">
          <el-select v-model="form.deviceType" placeholder="请选择设备类型">
            <el-option
              v-for="dict in dict.type.special_device_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格型号" prop="specificationModel">
          <el-input v-model="form.specificationModel" placeholder="请输入规格型号" />
        </el-form-item>
        <el-form-item label="动力方式" prop="powerMethod">
          <el-select v-model="form.powerMethod" placeholder="请选择动力方式">
            <el-option
              v-for="dict in dict.type.power_method"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="制造单位" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入制造单位" />
        </el-form-item>
        <el-form-item label="制造日期" prop="manufactureDate">
          <el-date-picker clearable
                          v-model="form.manufactureDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择制造日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出厂编号" prop="serialNumber">
          <el-input v-model="form.serialNumber" placeholder="请输入出厂编号" />
        </el-form-item>
        <el-form-item label="投入使用日期" prop="useDate">
          <el-date-picker clearable
                          v-model="form.useDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择投入使用日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="额定载荷" prop="ratedLoad">
          <el-input v-model="form.ratedLoad" placeholder="请输入额定载荷" />
        </el-form-item>
        <el-form-item label="设备(注册)代码" prop="deviceCode">
          <el-input v-model="form.deviceCode" placeholder="请输入设备(注册)代码" />
        </el-form-item>
        <el-form-item label="登记机关" prop="registrationAuthority">
          <el-input v-model="form.registrationAuthority" placeholder="请输入登记机关" />
        </el-form-item>
        <el-form-item label="登记证编号" prop="registrationNumber">
          <el-input v-model="form.registrationNumber" placeholder="请输入登记证编号" />
        </el-form-item>
        <el-form-item label="登记证图片" prop="registrationCertificate">
          <image-upload v-model="form.registrationCertificate"/>
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
import { listInventory, getInventory, addInventory, updateInventory, delInventory } from "@/api/special/inventory";
import { listLedger } from "@/api/equip/ledger";

export default {
  name: "Inventory",
  dicts: ['power_method', 'special_device_type', 'equip_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '设备名称', visible: true },
        { key: 2, label: '单位内编号', visible: true },
        { key: 3, label: '设备类型', visible: true },
        { key: 4, label: '规格型号', visible: true },
        { key: 5, label: '动力方式', visible: true },
        { key: 6, label: '制造单位', visible: true },
        { key: 7, label: '制造日期', visible: true },
        { key: 8, label: '出厂编号', visible: true },
        { key: 9, label: '投入使用日期', visible: true },
        { key: 10, label: '额定载荷', visible: true },
        { key: 11, label: '设备(注册)代码', visible: true },
        { key: 12, label: '登记机关', visible: true },
        { key: 13, label: '登记证编号', visible: true },
        { key: 14, label: '登记证图片', visible: true },
        { key: 15, label: '备注', visible: true },
        { key: 16, label: '创建时间', visible: true },
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
      // 场(厂)内专用机动车辆表格数据
      inventoryList: [],
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
        deviceName: null,
        internalNumber: null,
        deviceType: null,
        powerMethod: null,
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
        deviceName: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
        internalNumber: [
          { required: true, message: "单位内编号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询场(厂)内专用机动车辆列表 */
    getList() {
      this.loading = true;
      listInventory(this.queryParams).then(response => {
        this.inventoryList = response.rows;
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
        deviceName: null,
        internalNumber: null,
        deviceType: null,
        specificationModel: null,
        powerMethod: null,
        manufacturer: null,
        manufactureDate: null,
        serialNumber: null,
        useDate: null,
        ratedLoad: null,
        deviceCode: null,
        registrationAuthority: null,
        registrationNumber: null,
        registrationCertificate: null,
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
      this.title = "添加场(厂)内专用机动车辆";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInventory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改场(厂)内专用机动车辆";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInventory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInventory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除场(厂)内专用机动车辆编号为"' + ids + '"的数据项？').then(function() {
        return delInventory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('special/inventory/export', {
        ...this.queryParams
      }, `inventory_${new Date().getTime()}.xlsx`)
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
      this.form.deviceName = row.equName;
      this.form.specificationModel = row.specification;
      this.equipSelectorOpen = false;
    }
  }
};
</script>
