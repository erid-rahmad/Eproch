<template>
  <div class="regist-detail">
    <el-form label-position="left" label-width="250px" :model="mainForm" size="mini">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <el-form-item label="Title" prop="prequalificationName" required>
            <el-input v-model="mainForm.prequalificationName" disabled class="form-input"></el-input>
          </el-form-item>
          <el-form-item label="Registration Start Date" prop="announcementPublishDate">
            <el-date-picker
              v-model="mainForm.announcementPublishDate"
              :format="dateDisplayFormat"
              disabled
              size="mini"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="Registration End Date" prop="announcementEndDate">
            <el-date-picker
              v-model="mainForm.announcementEndDate"
              :format="dateDisplayFormat"
              disabled
              size="mini"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="Remaining Time" prop="timeRemaining">
            <span>{{ timeRemaining }}</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-button type="primary" v-if="accButton"  @click="minatAction">Accept Invitation</el-button>
    <el-dialog :visible.sync="showDialog" title="Upload Document Registration" width="40%">
      <el-form>
        <el-table :data="uploadModels" size="mini" style="width: 100%" border>
          <el-table-column label="No" align="center" min-width="30">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="Document Name" min-width="180" prop="name"> </el-table-column>
          <el-table-column label="File" min-width="180">
            <template slot-scope="{ row }">
              <el-form-item>
                <el-upload
                  :ref="row.name"
                  v-model="row.file"
                  :action="action"
                  class="upload-demo"
                  :limit="1"
                  :multiple="false"
                  :accept="accept"
                  :before-upload="handleBeforeUpload"
                  :on-change="onUploadChange"
                  :on-preview="handlePreview"
                  :on-exceed="handleExceed"
                  :on-remove="(file,fileList)=>handleRemove(file,fileList,row)"
                  :on-error="onUploadError"
                  :on-success="(response, file)=>onUploadSuccess(response,file,row)"
                >
                  <el-button size="mini" type="primary"> Select Document </el-button>
                  <div slot="tip" class="el-upload__tip">pdf/jpg/png files with a size less than 2mb</div>
                </el-upload>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="register" type="primary">Register</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./regist-detail.component.ts"></script>

<style lang="scss">
.form-input {
  width: 50%;
}
</style>