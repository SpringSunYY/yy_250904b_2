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
      <el-form-item label="设备位号" prop="equipmentTag">
        <el-input
          v-model="queryParams.equipmentTag"
          placeholder="请输入设备位号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
          v-model="queryParams.remarks"
          placeholder="请输入备注"
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
          v-hasPermi="['equip:tank:add']"
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
          v-hasPermi="['equip:tank:edit']"
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
          v-hasPermi="['equip:tank:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['equip:tank:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tankList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" v-if="columns[0].visible" prop="id" />
      <el-table-column label="检查编号" align="center" v-if="columns[1].visible" prop="id">
        <template slot-scope="scope">
          JC{{ String(scope.row.id).padStart(4, '0') }}
        </template>
      </el-table-column>
        <el-table-column label="设备名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="equipmentName" />
        <el-table-column label="设备位号" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="equipmentTag" />
        <el-table-column label="主要材质" align="center" v-if="columns[4].visible" prop="mainMaterial">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.special_material" :value="scope.row.mainMaterial"/>
        </template>
      </el-table-column>
        <el-table-column label="容积" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible" prop="volume" />
        <el-table-column label="储存介质" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="storageMedium" />
        <el-table-column label="检查类型" align="center" v-if="columns[7].visible" prop="inspectionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_inspe" :value="scope.row.inspectionType"/>
        </template>
      </el-table-column>
        <el-table-column label="检查时间" align="center" v-if="columns[8].visible" prop="inspectionTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="检查人员" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible" prop="inspectors" />
        <el-table-column label="检查结果" align="center" v-if="columns[10].visible" prop="inspectionResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.equip_patrol" :value="scope.row.inspectionResult"/>
        </template>
      </el-table-column>
        <el-table-column label="问题描述" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="problemDescription" />
        <el-table-column label="处置措施" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible" prop="disposalMeasures" />
        <el-table-column label="检查记录" align="center" v-if="columns[13].visible" prop="inspectionRecord" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.inspectionRecord">
            <el-tooltip placement="top">
              <div slot="content">
                  <div v-for="(file,index) in scope.row.inspectionRecord.split(',')"
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
        <el-table-column label="整改记录" align="center" v-if="columns[14].visible" prop="rectificationRecord" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.rectificationRecord">
            <el-tooltip placement="top">
              <div slot="content">
                  <div v-for="(file,index) in scope.row.rectificationRecord.split(',')"
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
        <el-table-column label="完好性评价报告" align="center" v-if="columns[15].visible" prop="integrityReport" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.integrityReport">
            <el-tooltip placement="top">
              <div slot="content">
                  <div v-for="(file,index) in scope.row.integrityReport.split(',')"
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
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible" prop="remarks" />
        <el-table-column label="创建时间" align="center" v-if="columns[17].visible" prop="createdTime" width="180">
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
            v-hasPermi="['equip:tank:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['equip:tank:remove']"
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

    <!-- 添加或修改常压储罐检查对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="设备名称" prop="equipmentName">
          <el-row>
            <el-col :span="20">
              <el-select
                v-model="form.equipmentName"
                placeholder="请选择设备"
                @change="handleEquipChange"
                style="width: 100%"
              >
                <el-option
                  v-for="item in equipList"
                  :key="item.equipId"
                  :label="item.equName"
                  :value="item.equName"
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
        <el-form-item label="设备位号" prop="equipmentTag">
          <el-input v-model="form.equipmentTag" placeholder="请输入设备位号" />
        </el-form-item>
        <el-form-item label="主要材质" prop="mainMaterial">
          <el-select v-model="form.mainMaterial" placeholder="请选择主要材质">
            <el-option
              v-for="dict in dict.type.special_material"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="容积" prop="volume">
          <el-input v-model="form.volume" placeholder="请输入容积" />
        </el-form-item>
        <el-form-item label="储存介质" prop="storageMedium">
          <el-input v-model="form.storageMedium" placeholder="请输入储存介质" />
        </el-form-item>
        <el-form-item label="检查类型" prop="inspectionType">
          <el-select v-model="form.inspectionType" placeholder="请选择检查类型">
            <el-option
              v-for="dict in dict.type.equip_inspe"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检查时间" prop="inspectionTime">
          <el-date-picker clearable
            v-model="form.inspectionTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择检查时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="检查人员" prop="inspectors">
          <el-select v-model="form.inspectors" multiple placeholder="请选择检查人员" filterable remote :remote-method="queryUsers"
                     :loading="userLoading">
            <el-option
              v-for="user in userList"
              :key="user.userId"
              :label="user.nickName"
              :value="user.nickName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="检查结果" prop="inspectionResult">
          <el-select v-model="form.inspectionResult" placeholder="请选择检查结果">
            <el-option
              v-for="dict in dict.type.equip_patrol"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述" prop="problemDescription">
          <el-input v-model="form.problemDescription" placeholder="请输入问题描述" />
        </el-form-item>
        <el-form-item label="处置措施" prop="disposalMeasures">
          <el-input v-model="form.disposalMeasures" placeholder="请输入处置措施" />
        </el-form-item>
        <el-form-item label="检查记录" prop="inspectionRecord">
          <file-upload v-model="form.inspectionRecord"/>
        </el-form-item>
        <el-form-item label="整改记录" prop="rectificationRecord">
          <file-upload v-model="form.rectificationRecord"/>
        </el-form-item>
        <el-form-item label="完好性评价报告" prop="integrityReport">
          <file-upload v-model="form.integrityReport"/>
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
import { getTank, listTank, updateTank, addTank, delTank } from "@/api/equip/tank";
import { listLedger } from "@/api/equip/ledger";
import { listUser } from "@/api/system/user";

export default {
  name: "Tank",
  dicts: ['special_material', 'equip_patrol', 'equip_inspe', 'equip_type', 'equip_levels', 'equip_location'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '序号', visible: true },
        { key: 1, label: '检查编号', visible: true },
          { key: 2, label: '设备名称', visible: true },
          { key: 3, label: '设备位号', visible: true },
          { key: 4, label: '主要材质', visible: true },
          { key: 5, label: '容积', visible: true },
          { key: 6, label: '储存介质', visible: true },
          { key: 7, label: '检查类型', visible: true },
          { key: 8, label: '检查时间', visible: true },
          { key: 9, label: '检查人员', visible: true },
          { key: 10, label: '检查结果', visible: true },
          { key: 11, label: '问题描述', visible: true },
          { key: 12, label: '处置措施', visible: true },
          { key: 13, label: '检查记录', visible: true },
          { key: 14, label: '整改记录', visible: true },
          { key: 15, label: '完好性评价报告', visible: true },
          { key: 16, label: '备注', visible: true },
          { key: 17, label: '创建时间', visible: true },
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
        // 常压储罐检查表格数据
        tankList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否显示设备选择弹出层
        equipSelectorOpen: false,
        // 是否显示用户选择弹出层
        userSelectorOpen: false,
        // 设备查询参数
        equipQueryParams: {
          pageNum: 1,
          pageSize: 10,
          equName: undefined,
          equCode: undefined
        },
        // 用户查询参数
        userQueryParams: {
          pageNum: 1,
          pageSize: 100,
          userName: undefined,
          nickName: undefined
        },
        // 设备列表
        equipList: [],
        // 设备选择器列表
        equipListSelector: [],
        // 用户列表
        userList: [],
        // 用户选择器列表
        userListSelector: [],
        // 设备总数
        equipTotal: 0,
        // 用户总数
        userTotal: 0,
        // 设备加载
        equipLoading: false,
        // 用户加载
        userLoading: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          equipmentName: null,
          equipmentTag: null,
          remarks: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          equipmentName: [
            { required: true, message: "设备名称不能为空", trigger: "blur" }
          ],
          equipmentTag: [
            { required: true, message: "设备位号不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询常压储罐检查列表 */
      getList() {
        this.loading = true;
        listTank(this.queryParams).then(response => {
          this.tankList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      //获取文件名 此功能只可以下载只有一个文件的
      getFileName(filePath) {
        if (filePath == null) {
          return
        }
        // 提取文件名或根据需求生成文件名
        return filePath.substring(filePath.lastIndexOf('/') + 1)
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
          equipmentName: null,
          equipmentTag: null,
          mainMaterial: null,
          volume: null,
          storageMedium: null,
          inspectionType: null,
          inspectionTime: null,
          inspectors: null,
          inspectionResult: null,
          problemDescription: null,
          disposalMeasures: null,
          inspectionRecord: null,
          rectificationRecord: null,
          integrityReport: null,
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
        this.title = "添加常压储罐检查";
        // 获取设备列表
        this.getEquipList();
        // 获取用户列表
        this.getUserList();
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getTank(id).then(response => {
          this.form = response.data;
          // 处理检查人员数据，确保是数组格式
          if (this.form.inspectors && typeof this.form.inspectors === 'string') {
            this.form.inspectors = this.form.inspectors.split(',').map(item => item.trim()).filter(item => item);
          } else if (!this.form.inspectors) {
            this.form.inspectors = [];
          }
          this.open = true;
          this.title = "修改常压储罐检查";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            // 处理检查人员数据，将数组转换为逗号分隔的字符串
            const formCopy = { ...this.form };
            if (Array.isArray(formCopy.inspectors)) {
              formCopy.inspectors = formCopy.inspectors.join(',');
            }

            if (this.form.id != null) {
              updateTank(formCopy).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addTank(formCopy).then(response => {
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
        this.$modal.confirm('是否确认删除常压储罐检查编号为"' + ids + '"的数据项？').then(function() {
          return delTank(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('equip/tank/export', {
          ...this.queryParams
        }, `tank_${new Date().getTime()}.xlsx`)
      },
      /** 设备选择变化处理 */
      handleEquipChange(val) {
        // 从设备台账列表中查找选中的设备
        const selectedDevice = this.equipList.find(item => item.equName === val);
        if (selectedDevice) {
          // 设置设备位号
          this.form.equipmentTag = selectedDevice.equCode;
        } else {
          this.form.equipmentTag = '';
        }
      },
      /** 获取设备列表 */
      getEquipList() {
        this.equipLoading = true;
        listLedger(this.equipQueryParams).then(response => {
          this.equipListSelector = response.rows;
          this.equipTotal = response.total;
          this.equipLoading = false;

          // 同时更新下拉框中的设备列表
          if (!this.equipQueryParams.equName && !this.equipQueryParams.equCode) {
            this.equipList = response.rows;
          }
        });
      },
      /** 获取用户列表 */
      getUserList() {
        this.userLoading = true;
        listUser(this.userQueryParams).then(response => {
          this.userListSelector = response.rows;
          this.userTotal = response.total;
          this.userLoading = false;

          // 同时更新下拉框中的用户列表
          if (!this.userQueryParams.userName && !this.userQueryParams.nickName) {
            this.userList = response.rows;
          }
        });
      },
      // 查询用户（支持远程搜索）
      queryUsers(query) {
        if (query !== undefined) {
          this.userQueryParams.nickName = query;
          this.getUserList();
        }
      },
      /** 设备搜索 */
      handleEquipQuery() {
        this.equipQueryParams.pageNum = 1;
        this.getEquipList();
      },
      /** 重置设备搜索 */
      resetEquipQuery() {
        this.resetForm('equipQueryForm');
        this.handleEquipQuery();
      },
      /** 选择设备 */
      handleSelectEquip(row) {
        this.form.equipmentName = row.equName;
        this.form.equipmentTag = row.equCode;
        this.equipSelectorOpen = false;
      },
      /** 打开设备选择器 */
      openEquipSelector() {
        this.equipSelectorOpen = true;
        this.resetEquipQuery();
      }
    }
};
</script>
