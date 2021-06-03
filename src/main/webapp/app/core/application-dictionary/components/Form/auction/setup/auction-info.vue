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
          <el-select
            v-model="auction.currencyId"
            clearable
            filterable
            placeholder="Select Currency"
          >
            <el-option
              v-for="item in currencyOptions"
              :key="item.id"
              :label="item.code"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Owner" prop="ownerUserId">
          <el-select
            v-model="auction.ownerUserId"
            clearable
            filterable
            placeholder="Select Owner"
          >
            <el-option
              v-for="item in ownerOptions"
              :key="item.id"
              :label="item.userLogin"
              :value="item.id"
            ></el-option>
          </el-select>
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
        <el-form-item label="Department">
          <el-select
            v-model="auction.costCenterId"
            clearable
            filterable
            placeholder="Select Department"
          >
            <el-option
              v-for="item in departmentOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts" src="./auction-info.component.ts"></script>
