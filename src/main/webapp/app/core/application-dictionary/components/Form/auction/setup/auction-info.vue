<template>
  <el-form
    v-loading="loading"
    ref="auctionInfoForm"
    class="auction-info"
    :disabled="readOnly"
    :label-position="formSettings.labelPosition"
    :label-width="formSettings.labelWidth"
    :model="auction"
    :rules="validationSchema"
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
            v-model="auction.documentNo"
            ref="documentNo"
            clearable
            :disabled="editMode && !!auction.documentNo"
          ></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="gutterSize">
      <el-col
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Title" prop="name">
          <el-input
            v-model="auction.name"
            clearable
          ></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutterSize">
      <el-col
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Description">
          <el-input
            v-model="auction.description"
            :autosize="{ minRows: 1, maxRows: 3}"
            clearable
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="gutterSize">
      <el-col
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Currency">
          <ad-input-lookup
            v-model="auction.currencyId"
            placeholder="Select Currency"
            table-name="c_currency"
          ></ad-input-lookup>
        </el-form-item>
      </el-col>
      <el-col
        v-if="!isVendor"
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Owner" prop="ownerUserId">
          <ad-input-lookup
            v-model="auction.ownerUserId"
            placeholder="Select Owner"
            table-name="ad_user"
            :label-fields="['userLogin']"
            :query="['employee.equals=true']"
          ></ad-input-lookup>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row
      v-if="!isVendor"
      :gutter="gutterSize"
    >
      <el-col
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Department">
          <ad-input-lookup
            v-model="auction.costCenterId"
            placeholder="Select Department"
            table-name="c_cost_center"
          ></ad-input-lookup>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts" src="./auction-info.component.ts"></script>
