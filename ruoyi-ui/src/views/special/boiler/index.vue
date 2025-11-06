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
      <el-form-item label="锅炉种类" prop="boilerType">
        <el-select v-model="queryParams.boilerType" placeholder="请选择锅炉种类" clearable>
          <el-option
            v-for="dict in dict.type.boiler_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="燃料种类" prop="fuelType">
        <el-select v-model="queryParams.fuelType" placeholder="请选择燃料种类" clearable>
          <el-option
            v-for="dict in dict.type.fuel_type"
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
          v-hasPermi="['special:boiler:add']"
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
          v-hasPermi="['special:boiler:edit']"
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
          v-hasPermi="['special:boiler:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['special:boiler:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="boilerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="boilerId" />
        <el-table-column label="设备编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="deviceNumber" />
        <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="deviceName" />
        <el-table-column label="锅炉种类" align="center" v-if="columns[3].visible" prop="boilerType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.boiler_type" :value="scope.row.boilerType"/>
        </template>
      </el-table-column>
        <el-table-column label="规格型号" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible" prop="deviceModel" />
        <el-table-column label="制造厂家" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="manufacturer" />
        <el-table-column label="制造日期" align="center" v-if="columns[6].visible" prop="manufactureDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.manufactureDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="出厂编号" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="serialNumber" />
        <el-table-column label="安装单位" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible" prop="installationUnit" />
        <el-table-column label="安装日期" align="center" v-if="columns[9].visible" prop="installationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="验收日期" align="center" v-if="columns[10].visible" prop="acceptanceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.acceptanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="额定蒸发量" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="ratedEvaporation" />
        <el-table-column label="热功率" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="thermalPower" />
        <el-table-column label="额定工作压力" :show-overflow-tooltip="true" align="center" v-if="columns[13].visible" prop="ratedWorkingPressure" />
        <el-table-column label="额定工作温度" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible" prop="ratedWorkingTemperature" />
        <el-table-column label="燃料种类" align="center" v-if="columns[15].visible" prop="fuelType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fuel_type" :value="scope.row.fuelType"/>
        </template>
      </el-table-column>
        <el-table-column label="受热面积" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible" prop="heatingArea" />
        <el-table-column label="本体重量" :show-overflow-tooltip="true" align="center" v-if="columns[17].visible" prop="bodyWeight" />
        <el-table-column label="设备(注册)代码" :show-overflow-tooltip="true" align="center" v-if="columns[18].visible" prop="deviceCode" />
        <el-table-column label="登记机关" :show-overflow-tooltip="true" align="center" v-if="columns[19].visible" prop="registrationAuthority" />
        <el-table-column label="登记证编号" :show-overflow-tooltip="true" align="center" v-if="columns[20].visible" prop="registrationNumber" />
        <el-table-column label="登记证文本" align="center" v-if="columns[21].visible" prop="registrationCertificate" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.registrationCertificate" :width="50" :height="50"/>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[22].visible" prop="remarks" />
        <el-table-column label="创建时间" align="center" v-if="columns[23].visible" prop="createdTime" width="180">
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
            v-hasPermi="['special:boiler:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['special:boiler:remove']"
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

    <!-- 添加或修改锅炉对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备编号" prop="deviceNumber">
          <el-input v-model="form.deviceNumber" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="锅炉种类" prop="boilerType">
          <el-select v-model="form.boilerType" placeholder="请选择锅炉种类">
            <el-option
              v-for="dict in dict.type.boiler_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格型号" prop="deviceModel">
          <el-input v-model="form.deviceModel" placeholder="请输入规格型号" />
        </el-form-item>
        <el-form-item label="制造厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" placeholder="请输入制造厂家" />
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
        <el-form-item label="验收日期" prop="acceptanceDate">
          <el-date-picker clearable
            v-model="form.acceptanceDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择验收日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="额定蒸发量" prop="ratedEvaporation">
          <el-input v-model="form.ratedEvaporation" placeholder="请输入额定蒸发量" />
        </el-form-item>
        <el-form-item label="热功率" prop="thermalPower">
          <el-input v-model="form.thermalPower" placeholder="请输入热功率" />
        </el-form-item>
        <el-form-item label="额定工作压力" prop="ratedWorkingPressure">
          <el-input v-model="form.ratedWorkingPressure" placeholder="请输入额定工作压力" />
        </el-form-item>
        <el-form-item label="额定工作温度" prop="ratedWorkingTemperature">
          <el-input v-model="form.ratedWorkingTemperature" placeholder="请输入额定工作温度" />
        </el-form-item>
        <el-form-item label="燃料种类" prop="fuelType">
          <el-select v-model="form.fuelType" placeholder="请选择燃料种类">
            <el-option
              v-for="dict in dict.type.fuel_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="受热面积" prop="heatingArea">
          <el-input v-model="form.heatingArea" placeholder="请输入受热面积" />
        </el-form-item>
        <el-form-item label="本体重量" prop="bodyWeight">
          <el-input v-model="form.bodyWeight" placeholder="请输入本体重量" />
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
        <el-form-item label="登记证文本" prop="registrationCertificate">
          <image-upload v-model="form.registrationCertificate"/>
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
  </div>
</template>

<script>
import { getBoiler, listBoiler, addBoiler, updateBoiler, delBoiler } from "@/api/special/boiler";

export default {
  name: "Boiler",
  dicts: ['boiler_type', 'fuel_type'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
          { key: 1, label: '设备编号', visible: true },
          { key: 2, label: '设备名称', visible: true },
          { key: 3, label: '锅炉种类', visible: true },
          { key: 4, label: '规格型号', visible: true },
          { key: 5, label: '制造厂家', visible: true },
          { key: 6, label: '制造日期', visible: true },
          { key: 7, label: '出厂编号', visible: true },
          { key: 8, label: '安装单位', visible: true },
          { key: 9, label: '安装日期', visible: true },
          { key: 10, label: '验收日期', visible: true },
          { key: 11, label: '额定蒸发量', visible: true },
          { key: 12, label: '热功率', visible: true },
          { key: 13, label: '额定工作压力', visible: true },
          { key: 14, label: '额定工作温度', visible: true },
          { key: 15, label: '燃料种类', visible: true },
          { key: 16, label: '受热面积', visible: true },
          { key: 17, label: '本体重量', visible: true },
          { key: 18, label: '设备(注册)代码', visible: true },
          { key: 19, label: '登记机关', visible: true },
          { key: 20, label: '登记证编号', visible: true },
          { key: 21, label: '登记证文本', visible: true },
          { key: 22, label: '备注', visible: true },
          { key: 23, label: '创建时间', visible: true },
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
      // 锅炉表格数据
      boilerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: null,
        boilerType: null,
        fuelType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询锅炉列表 */
    getList() {
      this.loading = true;
      listBoiler(this.queryParams).then(response => {
        this.boilerList = response.rows;
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
        boilerId: null,
        deviceNumber: null,
        deviceName: null,
        boilerType: null,
        deviceModel: null,
        manufacturer: null,
        manufactureDate: null,
        serialNumber: null,
        installationUnit: null,
        installationDate: null,
        acceptanceDate: null,
        ratedEvaporation: null,
        thermalPower: null,
        ratedWorkingPressure: null,
        ratedWorkingTemperature: null,
        fuelType: null,
        heatingArea: null,
        bodyWeight: null,
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
      this.ids = selection.map(item => item.boilerId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加锅炉";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const boilerId = row.boilerId || this.ids
      getBoiler(boilerId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改锅炉";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.boilerId != null) {
            updateBoiler(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBoiler(this.form).then(response => {
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
      const boilerIds = row.boilerId || this.ids;
      this.$modal.confirm('是否确认删除锅炉编号为"' + boilerIds + '"的数据项？').then(function() {
        return delBoiler(boilerIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('special/boiler/export', {
        ...this.queryParams
      }, `boiler_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
