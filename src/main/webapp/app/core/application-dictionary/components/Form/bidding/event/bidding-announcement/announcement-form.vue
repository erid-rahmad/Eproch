<template>
  <div class="add-announcement">
    <el-form
      v-loading="loading"
      ref="mainForm"
      :model="formData"
      label-position="left"
      label-width="150px"
      :rules="validationSchema"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="18" :xl="8">
          <el-form-item label="Bidding No." prop="biddingId">
            <el-select v-model="formData.biddingId" clearable :disabled="!newRecord" filterable @change="retrieveVendorSuggestions">
              <el-option v-for="item in biddingData" :key="item.id" :label="item.documentNo" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="18" :xl="8">
          <el-form-item label="Bidding Title">
            <el-select v-model="formData.biddingId" clearable disabled filterable>
              <el-option v-for="item in biddingData" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="18" :xl="16">
          <el-form-item label="Description" prop="description">
            <html-editor v-model="formData.description" size="mini"></html-editor>
          </el-form-item>
          <el-form-item style="margin-top: .5rem">
            <el-button v-if="!hasAttachment" size="mini" type="primary" @click="attachmentFormVisible = true">
              <svg-icon name="icomoo/206-attachment"></svg-icon> Attachment
            </el-button>
            <el-button v-if="hasAttachment" icon="el-icon-view" size="mini" type="primary" @click="handlePreview">
              {{ attachmentName }}
            </el-button>
            <el-button v-if="hasAttachment" icon="el-icon-close" size="mini" type="primary" @click="cancelAttachment"></el-button>
            <el-button icon="el-icon-view" size="mini" type="primary" @click="emailPreviewVisible = true">
              Preview
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-divider content-position="left">
      <h4>Vendors</h4>
    </el-divider>
    <el-row>
      <el-col :xs="24" :sm="24" :lg="18" :xl="16">
        <el-table
          v-loading="loadingVendors"
          ref="biddingSchedule"
          border
          :data="vendorSuggestions"
          highlight-current-row
          size="mini"
          stripe
        >
          <el-table-column label="No" width="50">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column width="100" prop="vendorCode" label="Code" show-overflow-tooltip></el-table-column>
          <el-table-column label="Vendor" min-width="200" prop="vendorName" show-overflow-tooltip></el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog title="Email Preview" :visible.sync="emailPreviewVisible">
      <div v-html="formData.description"></div>
    </el-dialog>

    <el-dialog
      v-loading="publishing"
      :close-on-click-modal="!publishing"
      :close-on-press-escape="!publishing"
      title="Publish Email"
      :visible.sync="recipientListVisible"
      @open="retrieveEmailList"
    >
      <el-table v-loading="loadingEmailList" border :data="emailList" size="mini" stripe @selection-change="onRecipientSelectionChanged">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="Vendor Name" property="cVendorName" min-width="150" show-overflow-tooltip></el-table-column>
        <el-table-column label="Username" property="name" min-width="150" show-overflow-tooltip></el-table-column>
        <el-table-column label="Position" property="position" min-width="150" show-overflow-tooltip></el-table-column>
        <el-table-column label="Email" property="email" min-width="150" show-overflow-tooltip></el-table-column>
      </el-table>
      <template>
        <div slot="footer">
          <el-button :loading="publishing" type="primary" size="mini" @click="publish">Send</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog :show-close="false" title="Add Attachment" :visible.sync="attachmentFormVisible">
      <el-upload
        ref="docUpload"
        :accept="accept"
        :action="action"
        auto-upload
        :headers="uploadHeaders"
        :limit="limit"
        :before-upload="handleBeforeUpload"
        :on-error="onUploadError"
        :on-exceed="handleExceed"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="onUploadSuccess"
      >
        <el-button icon="el-icon-search" slot="trigger" type="primary">
          Select File
        </el-button>
        <span class="el-upload__tip" style="margin-left: 10px" slot="tip">
          Files with a size less than 5Mb
        </span>
      </el-upload>

      <div slot="footer">
        <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;" @click="attachmentFormVisible = false">
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary" @click="saveAttachment">
          {{ $t('entity.action.save') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./announcement-form.component.ts"></script>
<style lang="scss">
.compact .add-announcement {
  .el-table--mini {
    td,
    th {
      height: 35px;
    }
  }
}
</style>
<style lang="scss" scoped>
.add-announcement {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
