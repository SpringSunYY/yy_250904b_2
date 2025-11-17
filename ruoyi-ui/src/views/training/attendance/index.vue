<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="培训名称" prop="trainingName">
        <el-input
          v-model="queryParams.trainingName"
          placeholder="请输入培训名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="personnelName">
        <el-input
          v-model="queryParams.personnelName"
          placeholder="请输入姓名"
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
          v-hasPermi="['training:attendance:add']"
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
          v-hasPermi="['training:attendance:edit']"
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
          v-hasPermi="['training:attendance:remove']"
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
          v-hasPermi="['training:attendance:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attendanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id" v-if="columns[0].visible"/>
      <el-table-column label="培训名称" align="center" prop="trainingName" v-if="columns[1].visible"/>
      <el-table-column label="姓名" align="center" prop="personnelName" v-if="columns[2].visible"/>
      <el-table-column label="考试时间" align="center" prop="examTime" width="180" v-if="columns[3].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.examTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考试分数" align="center" prop="examScore" v-if="columns[4].visible"/>
      <el-table-column label="考核结果" align="center" prop="assessmentResult" v-if="columns[5].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_training_result" :value="scope.row.assessmentResult"/>
        </template>
      </el-table-column>
      <el-table-column label="考试试卷" align="center" prop="examPaper" v-if="columns[6].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.examPaper && scope.row.examPaper.length > 0">
            <div v-for="(file, index) in scope.row.examPaper.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="是否需要补考" align="center" prop="needRetake" v-if="columns[7].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.needRetake"/>
        </template>
      </el-table-column>
      <el-table-column label="补考时间" align="center" prop="retakeTime" width="180" v-if="columns[8].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.retakeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="补考成绩" align="center" prop="retakeScore" v-if="columns[9].visible"/>
      <el-table-column label="最终判定结果" align="center" prop="finalResult" v-if="columns[10].visible">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractor_final_result" :value="mapFinalResultValue(scope.row.finalResult)"/>
        </template>
      </el-table-column>
      <el-table-column label="补考试卷" align="center" prop="retakePaper" v-if="columns[11].visible">
        <template slot-scope="scope">
          <div v-if="scope.row.retakePaper && scope.row.retakePaper.length > 0">
            <div v-for="(file, index) in scope.row.retakePaper.split(',')" :key="index" class="file-item">
              <el-link type="primary" @click="downloadFile(file)">
                <i class="el-icon-document"></i>
                {{ getFileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[12].visible"/>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180" v-if="columns[13].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="columns[14].visible">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['training:attendance:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['training:attendance:remove']"
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

    <!-- 添加或修改人员参训记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="培训名称" prop="trainingId">
          <el-select v-model="form.trainingId" placeholder="请选择培训名称" clearable @change="handleTrainingChange">
            <el-option
              v-for="training in trainingRecordsList"
              :key="training.id"
              :label="training.trainingName"
              :value="training.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="personnelName">
          <el-input v-model="form.personnelName" placeholder="请输入姓名" readonly>
            <el-button slot="append" icon="el-icon-search" @click="handleSelectPersonnel"
                       :disabled="!form.trainingName"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="考试时间" prop="examTime">
          <el-date-picker clearable
                          v-model="form.examTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择考试时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考试分数" prop="examScore">
          <el-input v-model="form.examScore" placeholder="请输入考试分数"/>
        </el-form-item>
        <el-form-item label="考核结果" prop="assessmentResult">
          <el-select v-model="form.assessmentResult" placeholder="请选择考核结果">
            <el-option
              v-for="dict in dict.type.contractor_training_result"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考试试卷" prop="examPaper">
          <file-upload v-model="form.examPaper"/>
        </el-form-item>
        <el-form-item label="是否需要补考" prop="needRetake">
          <el-select v-model="form.needRetake" placeholder="请选择是否需要补考" @change="handleNeedRetakeChange">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="补考时间" prop="retakeTime">
          <el-date-picker clearable
                          v-model="form.retakeTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择补考时间"
                          :disabled="form.needRetake === 'N'">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="补考成绩" prop="retakeScore">
          <el-input v-model="form.retakeScore" placeholder="请输入补考成绩" :disabled="form.needRetake === 'N'"/>
        </el-form-item>
        <el-form-item label="最终判定结果" prop="finalResult">
          <el-select v-model="form.finalResult" placeholder="请选择最终判定结果" :disabled="form.needRetake === 'N'">
            <el-option
              v-for="dict in dict.type.contractor_final_result"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="补考试卷" prop="retakePaper">
          <file-upload v-model="form.retakePaper" :disabled="form.needRetake === 'N'"/>
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

    <!-- 人员选择对话框 -->
    <el-dialog :title="personDialogTitle" :visible.sync="personSelectOpen" width="800px" append-to-body>
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

      <el-table v-loading="personLoading" :data="personList" @row-dblclick="handleSelectPersonRow"
                highlight-current-row>
        <el-table-column label="项目名称" align="center" prop="projectName"/>
        <el-table-column label="承包商" align="center" prop="contractorName"/>
        <el-table-column label="姓名" align="center" prop="personnelName"/>
        <el-table-column label="身份证号" align="center" prop="idCardNumber"/>
        <el-table-column label="职务" align="center" prop="title"/>
        <el-table-column label="工种" align="center" prop="workType"/>
        <el-table-column label="联系方式" align="center" prop="contactPhone"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSelectPersonRow(scope.row)"
            >选择
            </el-button>
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
import {listAttendance, getAttendance, delAttendance, addAttendance, updateAttendance} from "@/api/training/attendance";
import {listRecords} from "@/api/training/records";
import {listRegister} from "@/api/personnel/register";

export default {
  name: "Attendance",
  dicts: ['contractor_training_result', 'sys_yes_no', 'contractor_final_result'],
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
      // 人员参训记录表格数据
      attendanceList: [],
      // 培训记录列表
      trainingRecordsList: [],
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
        trainingName: null,
        personnelName: null,
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
        {key: 0, label: `序号`, visible: true},
        {key: 1, label: `培训名称`, visible: true},
        {key: 2, label: `姓名`, visible: true},
        {key: 3, label: `考试时间`, visible: true},
        {key: 4, label: `考试分数`, visible: true},
        {key: 5, label: `考核结果`, visible: true},
        {key: 6, label: `考试试卷`, visible: true},
        {key: 7, label: `是否需要补考`, visible: true},
        {key: 8, label: `补考时间`, visible: true},
        {key: 9, label: `补考成绩`, visible: true},
        {key: 10, label: `最终判定结果`, visible: true},
        {key: 11, label: `补考试卷`, visible: true},
        {key: 12, label: `备注`, visible: true},
        {key: 13, label: `创建时间`, visible: true},
        {key: 14, label: `操作`, visible: true}
      ],
      // 表单校验
      rules: {
        trainingName: [
          {required: true, message: "培训名称不能为空", trigger: "change"}
        ],
        personnelName: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ]
      },
      // 当前选中的培训记录
      selectedTraining: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询人员参训记录列表 */
    getList() {
      this.loading = true;
      listAttendance(this.queryParams).then(response => {
        this.attendanceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询培训记录列表 */
    getTrainingRecordsList() {
      listRecords({}).then(response => {
        this.trainingRecordsList = response.rows;
      });
    },
    // 处理培训名称变更
    handleTrainingChange() {
      // 清空之前选择的人员信息
      this.form.personnelName = "";
      this.form.personnelId = null;
      console.log(this.form.trainingId);
      // 查找选中的培训记录
      this.selectedTraining = this.trainingRecordsList.find(item => item.id === this.form.trainingId);
      if (this.selectedTraining) {
        this.form.trainingName = this.selectedTraining.trainingName;
        console.log(this.selectedTraining);
      }
    },
    // 处理是否需要补考变更
    handleNeedRetakeChange(value) {
      // 如果选择"否"(N)，则自动设置最终判定结果为"通过"
      if (value === 'N') {
        // 查找"通过"选项的值
        const passOption = this.dict.type.contractor_final_result.find(item => item.label === '通过');
        if (passOption) {
          this.form.finalResult = passOption.value;
        } else {
          // 如果找不到"通过"选项，则直接设置为"通过"
          this.form.finalResult = '通过';
        }

        // 清空补考相关字段
        this.form.retakeTime = null;
        this.form.retakeScore = null;
      }
    },
    // 打开人员选择对话框
    handleSelectPersonnel() {
      if (!this.form.trainingName) {
        this.$modal.msgWarning("请先选择培训名称");
        return;
      }

      this.personSelectOpen = true;
      // 初始化查询参数
      this.personQueryParams = {
        pageNum: 1,
        pageSize: 10,
        personnelName: null,
        idCardNumber: null
      };

      // 根据选中的培训记录中的承包商名称查询人员
      if (this.selectedTraining) {
        this.personQueryParams.contractorName = this.selectedTraining.contractorName;
      }

      this.getPersonList();
    },
    // 查询人员列表
    getPersonList() {
      this.personLoading = true;
      listRegister(this.personQueryParams).then(response => {
        this.personList = response.rows;
        this.personTotal = response.total;
        this.personLoading = false;
      }).catch(() => {
        this.personLoading = false;
      });
    },
    // 选择人员
    handleSelectPersonRow(row) {
      this.form.personnelName = row.personnelName;
      this.form.personnelId = row.personnelId;
      this.personSelectOpen = false;
    },
    // 搜索人员
    handlePersonQuery() {
      this.personQueryParams.pageNum = 1;
      this.getPersonList();
    },
    // 重置人员搜索
    resetPersonQuery() {
      this.personQueryParams = {
        pageNum: 1,
        pageSize: 10,
        personnelName: null,
        idCardNumber: null
      };
      // 重新设置承包商条件
      if (this.selectedTraining) {
        this.personQueryParams.contractorName = this.selectedTraining.contractorName;
      }
      this.getPersonList();
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
        trainingId: null,
        trainingName: null,
        personnelId: null,
        personnelName: null,
        examTime: null,
        examScore: null,
        assessmentResult: null,
        examPaper: null,
        needRetake: null,
        retakeTime: null,
        retakeScore: null,
        finalResult: null,
        retakePaper: null,
        createdBy: null,
        remark: null,
        createdAt: null,
        updatedBy: null,
        updatedAt: null
      };
      this.resetForm("form");
      this.selectedTraining = null;
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTrainingRecordsList(); // 获取培训记录列表
      this.open = true;
      this.title = "添加人员参训记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTrainingRecordsList(); // 获取培训记录列表
      const id = row.id || this.ids
      getAttendance(id).then(response => {
        this.form = response.data;
        // 查找对应的培训记录以设置selectedTraining
        this.selectedTraining = this.trainingRecordsList.find(item => item.trainingName === this.form.trainingName);
        this.open = true;
        this.title = "修改人员参训记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 设置培训ID
          if (this.selectedTraining) {
            this.form.trainingId = this.selectedTraining.id;
          }

          if (this.form.id != null) {
            updateAttendance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAttendance(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除人员参训记录编号为"' + ids + '"的数据项？').then(function () {
        return delAttendance(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('training/attendance/export', {
        ...this.queryParams
      }, `attendance_${new Date().getTime()}.xlsx`)
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
    // 映射最终判定结果值以匹配字典
    mapFinalResultValue(value) {
      if (value === '通过') {
        return '1';
      } else if (value === '不通过') {
        return '2';
      }
      return value;
    }
  }
};
</script>
