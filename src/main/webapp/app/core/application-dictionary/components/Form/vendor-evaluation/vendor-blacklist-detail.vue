<template>
  <div class="vendor-blacklist-detail">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="200px"
      :model="mainForm"
      size="mini"
    >
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Business Category">
            <el-select
              v-model="mainForm.businessCategoryId"
              class="form-input"
              clearable
              filterable
              :disabled="readOnly"
              placeholder="Select"
              @change="updateVendorList"
            >
              <el-option
                v-for="item in categoryOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Vendor Name">
            <el-select
              v-model="mainForm.vendorId"
              class="form-input"
              clearable
              filterable
              :disabled="readOnly"
              placeholder="Select"
              @change="onVendorChange(true)"
            >
              <el-option
                v-for="item in vendors"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Confirmation Attachment" prop="attachment">
            <el-upload
              ref="contractFile"
              v-model="file"
              :action="action"
              class="upload-demo"
              :limit="limit"
              :multiple="false"
              :accept="accept"
              :file-list="fileList"
              :before-upload="handleBeforeUpload"
              :on-change="onUploadChange"
              :on-preview="handlePreview"
              :on-exceed="handleExceed"
              :on-remove="handleRemove"
              :on-error="onUploadError"
              :on-success="onUploadSuccess"
            >
              <el-button
                size="mini"
                type="primary"
              >
                Select Document
              </el-button>
              <div
                slot="tip"
                class="el-upload__tip"
              >
                pdf/jpg/png files with a size less than 2mb
              </div>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Sub Business Category">
            <el-select
              v-model="mainForm.subBusinessCategoryId"
              :loading="loadingVendorList"
              class="form-input"
              clearable
              filterable
              :disabled="readOnly"
              placeholder="Select"
              @change="updateVendorList"
            >
              <el-option
                v-for="item in categoryOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Type">
            <el-select
              v-model="mainForm.type"
              :disabled="readOnly"
            >
              <el-option
                v-for="item in blacklistTypes"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Report Date">
            <el-date-picker
              v-model="mainForm.reportDate"
              clearable
              :disabled="readOnly"
              :format="dateDisplayFormat"
              placeholder="Pick a date"
              size="mini"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Message">
            <el-input
              v-model="mainForm.notes"
              clearable
              :disabled="readOnly"
              :rows="7"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-divider />

    <el-form
      ref="filterFormPersonal"
      label-position="left"
      label-width="200px"
      :model="filterPersonal"
      size="mini"
    >
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Personal">
            <el-select
              v-model="filterPersonal.id"
              :loading="loadingVendorList"
              class="form-input"
              clearable
              filterable
              :disabled="readOnly"
              @change="assignName(true)"
              placeholder="Select User"
            >
              <el-option
                v-for="item in userOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col>
          <el-button
            class="button" 
            icon="el-icon-plus" 
            size="mini" 
            type="primary"
            @click="addPersonal">
            Add Blacklist
          </el-button>
        </el-col>
      </el-row>
      <el-table
        ref="personalGrid"
        border
        :data="mainForm.users"
        highlight-current-row
        size="mini"
        stripe
        style="width: 100%"
      >
        <el-table-column
          align="center"
          fixed
          type="selection"
          width="48"
        ></el-table-column>
        <el-table-column
          label="Name"
          min-width="150"
          prop="picName"
          show-overflow-tooltip
          sortable
        ></el-table-column>
        <el-table-column
          label="ID"
          min-width="100"
          prop="picId"
          sortable
        ></el-table-column>
        <el-table-column
          label="Position"
          min-width="150"
          sortable
        ></el-table-column>
        <el-table-column
          label="Note"
          min-width="200"
        >
          <template slot-scope="{ row }">
            <el-input
              v-model="row.notes"
              :disabled="readOnly"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column min-width="60">
          <template slot-scope="{ row, $index }">
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="removeRow(row, $index, true)" />
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <el-divider></el-divider>
    <el-form
      ref="filterFormShareHolder"
      label-position="left"
      label-width="200px"
      :model="filterShareHolder"
      size="mini"
    >
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Share Holder">
            <el-select
              v-model="filterShareHolder.id"
              :loading="loadingVendorList"
              class="form-input"
              clearable
              filterable
              :disabled="readOnly"
              @change="assignName()"
              placeholder="Select Shareholder"
            >
              <el-option
                v-for="item in shareholderOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col>
          <el-button
            class="button" 
            icon="el-icon-plus" 
            size="mini" 
            type="primary"
            @click="addShareholder">
            Add Blacklist
          </el-button>
        </el-col>
      </el-row>
      <el-table
        ref="personalGrid"
        border
        :data="mainForm.shareholders"
        highlight-current-row
        size="mini"
        stripe
        style="width: 100%"
      >
        <el-table-column
          align="center"
          fixed
          type="selection"
          width="48"
        ></el-table-column>
        <el-table-column
          label="Name"
          min-width="150"
          prop="functionaryName"
          show-overflow-tooltip
          sortable
        ></el-table-column>
        <el-table-column
          label="ID"
          min-width="100"
          prop="functionaryId"
          sortable
        ></el-table-column>
        <el-table-column
          label="Position"
          min-width="150"
          sortable
        ></el-table-column>
        <el-table-column
          label="Note"
          min-width="200"
        >
          <template slot-scope="{ row }">
            <el-input
              v-model="row.notes"
              :disabled="readOnly"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column min-width="60">
          <template slot-scope="{ row, $index }">
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="removeRow(row, $index)" />
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </div>
</template>
<script lang="ts" src="./vendor-blacklist-detail.component.ts"></script>
<style lang="scss">
.vendor-blacklist-detail {
  padding: 4px 8px;
}
</style>
