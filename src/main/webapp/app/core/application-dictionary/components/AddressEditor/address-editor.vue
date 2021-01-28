<template>
  <el-popover
    v-model="visible"
    title="Edit Address"
    trigger="click"
    width="320"
    @show="onPopoverShow"
  >
    <el-form
      ref="form"
      label-position="left"
      label-width="128px"
      size="mini"
      :model="location"
      :rules="validationSchema"
    >
      <el-row>
        <el-col :span="24">
          <el-form-item :label="$t('opusWebApp.cLocation.address1')" prop="address1" required>
            <el-input v-model="location.address1" class="form-input" clearable max="100" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item :label="$t('opusWebApp.cLocation.address2')"  prop="address2">
            <el-input v-model="location.address2" class="form-input" clearable max="100" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item :label="$t('opusWebApp.cLocation.address3')"  prop="address3">
            <el-input v-model="location.address3" class="form-input" clearable max="100" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.address4')"  prop="address4">
            <el-input v-model="location.address4" class="form-input" clearable max="100" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.postalCode')"  prop="postalCode">
            <el-input v-model="location.postalCode" class="form-input" clearable max="10" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.country')"  prop="country">
            <el-select
              v-model="country"
              :auto-complete="false"
              class="form-input"
              clearable
              filterable
              placeholder="Select a Country"
              @change="onCountryChange"
            >
              <el-option
                v-for="item in countries"
                :key="item.id"
                :label="item.name"
                :value="jsonEncode(item, ['id', 'name', 'withRegion'])"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row
        v-if="countryHasRegion"
       
      >
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.region')"  prop="region">
            <el-select
              v-model="region"
              :auto-complete="false"
              class="form-input"
              clearable
              :disabled="!country"
              filterable
              placeholder="Select a Region"
              @change="onRegionChange"
            >
              <el-option
                v-for="item in regions"
                :key="item.id"
                :label="item.name"
                :value="jsonEncode(item, ['id', 'name'])"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.city')"  prop="cityId" required>
            <el-select
              v-model="location.cityId"
              :auto-complete="false"
              class="form-input"
              clearable
              :disabled="countryHasRegion ? !region : !country"
              filterable
              placeholder="Select a City"
            >
              <el-option
                v-for="item in cities"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.phone')"  prop="phone">
            <el-input v-model="location.phone" class="form-input" clearable max="20" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('opusWebApp.cLocation.fax')"  prop="fax">
            <el-input v-model="location.fax" class="form-input" clearable max="20" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <div style="text-align: right">
          <el-button size="mini" type="text" @click="visible = false">Cancel</el-button>
          <el-button type="primary" size="mini" @click="onSave">Save</el-button>
        </div>
      </el-row>
    </el-form>
    <el-input
      slot="reference"
      v-model="location.name"
      class="input"
      clearable
      readonly
      size="mini"
    />
  </el-popover>
</template>
<script lang="ts" src="./address-editor.component.ts"></script>
