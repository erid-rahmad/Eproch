<template>
  <div class="auction-prerequisite">
    <el-form
      v-loading="loading"
      ref="mainForm"
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="auction"
      :rules="auctionValidationSchema"
      :size="formSettings.size"
    >
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Auction No.">
            <el-input
              v-if="isInvitation"
              v-model="auction.auctionDocumentNo"
              disabled
            ></el-input>
            <el-input
              v-else
              v-model="auction.documentNo"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Title">
            <el-input
              v-model="auction.name"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            v-if="!isVendor"
            label="Prerequisite Template"
            prop="prerequisiteId"
          >
            <ad-input-lookup
              v-model="auction.prerequisiteId"
              :disabled="readOnly"
              :items.sync="prerequisiteOptions"
              placeholder="Select Template"
              table-name="c_auction_prerequisite"
              @change="onPrerequisiteChanged"
            ></ad-input-lookup>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-form
      v-loading="loadingPrerequisite"
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="prerequisite"
      :size="formSettings.size"
    >
      <html-editor
        v-model="prerequisite.description"
        disabled
        size="mini"
      ></html-editor>
      <el-form-item
        v-if="isVendor"
        label=""
        prop="acceptPrerequisite"
        style="margin-top: 5px"
      >
        <el-radio-group
          v-model="acceptPrerequisite"
          :disabled="auction.documentStatus === 'ACC' || auction.documentStatus === 'DCL'"
          @change="onPrerequisiteConfirmed">
          <el-radio :label="true">Accept</el-radio>
          <el-radio :label="false">Decline</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      width="40%"
      :visible.sync="acceptConfirmationVisible"
      :title="`${selectedAction} Invitation`"
    >
      <template>
        <p>Are you sure you want to {{ selectedAction }} the invitation?</p>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="onAcceptConfirmationClose"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-check"
            :type="acceptPrerequisite ? 'primary' : 'danger'"
            @click="confirmPrerequisite"
          >
            {{ selectedAction }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./auction-prerequisite.component.ts"></script>
