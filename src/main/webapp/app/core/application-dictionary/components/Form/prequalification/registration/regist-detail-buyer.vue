<template>
  <div class="regist-detail-buyer">
    <el-form label-position="left" label-width="250px" :model="mainForm" size="mini">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <el-form-item label="Prequalification Number" prop="documentNo">
            <el-input v-model="mainForm.documentNo" disabled class="form-input"></el-input>
          </el-form-item>
          <el-form-item label="Prequalification" prop="name">
            <el-input v-model="mainForm.name" disabled class="form-input"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-form label-position="left" label-width="250px" :model="mainForm" size="mini">
      <el-table border :data="registeredVendors" highlight-current-row size="mini" stripe>
        <el-table-column width="50" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="Joined Date" min-width="150" sortable>
          <template slot-scope="{ row }">
            {{ row.answerDate | formatDate }}
          </template>
        </el-table-column>
        <el-table-column label="Vendor Name" min-width="200" prop="vendorName" sortable show-overflow-tooltip></el-table-column>
        <el-table-column label="Type" min-width="150"> Invitation </el-table-column>
        <el-table-column label="View Registration Document" min-width="150">
          <template slot-scope="{ row }">
            <el-button class="btn-attachment" size="mini" type="primary" @click="loadAttachment(row)"> View </el-button>
          </template>
        </el-table-column>
        <el-table-column label="Evaluate" min-width="150">
          <template slot-scope="{ row }">
            <el-select v-model="row.pass" placeholder="Pass Fail">
              <el-option v-for="item in passfail" :key="item.value" :label="item.label" :value="item.value"/>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <el-button class="btn-attachment" size="mini" type="primary" :disabled="submit" @click="process()"> Submit </el-button>

    <el-dialog :visible.sync="showDialog" title="Document Registration" width="40%">
      <el-table :data="uploadModels" size="mini" style="width: 100%" border>
        <el-table-column label="No" align="center" min-width="30">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="Document Name" min-width="180" prop="name"> </el-table-column>
        <el-table-column label="File" min-width="180">
          <template slot-scope="{ row }">
            <el-button class="btn-attachment" size="mini" type="primary" @click="downloadFile(row)"> 
              {{row.file.fileName}} 
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./regist-detail-buyer.component.ts"></script>

<style lang="scss">
.form-input {
  width: 50%;
}
</style>