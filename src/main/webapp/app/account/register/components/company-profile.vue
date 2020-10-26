<template>
  <el-form
    ref="company"
    label-position="right"
    label-width="150px"
    size="mini"
    :model="company"
    :rules="rules">
    <el-divider content-position="left"><h4>{{ $t('register.basic.basic.title') }}</h4></el-divider>
    <el-row :gutter="columnSpacing">
        <el-col :span="12">
            <el-form-item :label="$t('register.basic.basic.name')" prop="name" required>
            <el-input
                class="form-input"
                clearable
                v-model="company.name" />
            </el-form-item>

            <el-row :gutter="columnSpacing">
                <el-col :span="12">
                    <el-form-item :label="$t('register.basic.basic.type')" prop="type" required>
                        <el-select
                            class="form-input"
                            clearable
                            filterable
                            v-model="company.type"
                            :placeholder="$t('register.form.select')"
                            @change="handleTypeChange"
                        >
                            <el-option
                            v-for="item in typeOptions"
                            :key="item.key"
                            :label="item.value"
                            :value="item.key"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item :label="$t('register.basic.basic.location')" prop="location" required>
                        <el-select
                            class="form-input"
                            filterable
                            v-model="company.location"
                            :placeholder="$t('register.form.select')"
                            @change="handleLocationChange"
                        >
                            <el-option
                            v-for="item in locationOptions"
                            :key="item.key"
                            :label="item.value"
                            :value="item.key"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item required>
            <el-switch
                style="padding-left: 20px;"
                v-model="company.branch"
                :active-text="$t('register.basic.basic.branch')"
            />
            </el-form-item>
        </el-col>

        <div v-if="tax">
            <el-col v-if="npwp" :span="12">
                <el-form-item :label="$t('register.basic.basic.npwp')" prop="npwp" required>
                    <el-input
                        class="form-input"
                        clearable
                        v-model="company.npwp"
                        v-inputmask
                        data-inputmask="'mask': '99.999.999.9-999.999'"
                        placeholder="__.___.___._-___.___"
                        />
                </el-form-item>
                <el-form-item :label="$t('register.basic.basic.npwpName')" prop="npwpName" required>
                    <el-input
                        class="form-input"
                        clearable
                        v-model="company.npwpName" />
                </el-form-item>
                <el-form-item
                :label="$t('register.basic.basic.npwpFile')"
                prop="file"
                required>
                    <el-upload
                        ref="upload"
                        v-model="company.file"
                        :action="action"
                        :accept="accept"
                        :file-list="fileList"
                        :limit="limit"
                        :before-upload="handleBeforeUpload"
                        :on-change="onUploadChange"
                        :on-exceed="handleExceed"
                        :on-remove="handleRemove"
                        :on-error="onUploadError"
                        :on-success="onUploadSuccess">
                        <el-button slot="trigger" type="primary" icon="el-icon-search">select file</el-button>
                        <span style="margin-left: 10px;" class="el-upload__tip" slot="tip">files with a size less than 5Mb</span>
                    </el-upload>
                </el-form-item>
            </el-col>
            <el-col v-else :span="12">
                <el-tooltip class="item" effect="dark" :content="$t('register.basic.basic.taxInformationNumber')" placement="top">
                    <el-form-item :label="$t('register.basic.basic.tin')" prop="tin" required>
                        <el-input
                            class="form-input"
                            clearable
                            v-model="company.tin" />
                    </el-form-item>
                </el-tooltip>
            </el-col>
        </div>

    </el-row>

    <el-divider content-position="left"><h4>{{ $t('register.basic.address.title') }}</h4></el-divider>
    <el-row :gutter="columnSpacing">
      <el-col :span="12">
        <el-form-item :label="$t('register.basic.address.address')" prop="address" required>
          <el-input
            class="form-input"
            clearable
            v-model="company.address"
            type="textarea"
            :autosize="{ maxRows: 3 }" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item :label="$t('register.basic.address.npwpAddress')" prop="npwpAddress">
          <el-input
            class="form-input"
            clearable
            v-model="company.npwpAddress"
            type="textarea"
            :autosize="{ maxRows: 3 }" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.country')" prop="country" required>
          <el-select
            class="form-input"
            clearable
            filterable
            v-model="company.country"
            :placeholder="$t('register.form.select')"
            @change="retrieveRegion($event, 1)">
            <el-option
              v-for="item in countryOptions"
              :key="item.key"
              :label="item.value"
              :value="item.key + '_' + item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.region')" prop="region" required>
          <el-select
            class="form-input"
            clearable
            filterable
            v-model="company.region"
            :placeholder="$t('register.form.select')"
            @change="retrieveCity($event, 1)">
            <el-option
              v-for="item in regionOptions"
              :key="item.key"
              :label="item.value"
              :value="item.key + '_' + item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.npwpCountry')" prop="npwpCountry">
          <el-select
            class="form-input"
            clearable
            filterable
            v-model="company.npwpCountry"
            :placeholder="$t('register.form.select')"
            @change="retrieveRegion($event, 2)">
            <el-option
              v-for="item in countryOptionsNpwp"
              :key="item.key"
              :label="item.value"
              :value="item.key + '_' + item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.npwpRegion')" prop="npwpRegion">
          <el-select
            class="form-input"
            clearable
            filterable
            v-model="company.npwpRegion"
            :placeholder="$t('register.form.select')"
            @change="retrieveCity($event, 2)">
            <el-option
              v-for="item in regionOptionsNpwp"
              :key="item.key"
              :label="item.value"
              :value="item.key + '_' + item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.city')" prop="city" required>
          <el-select
            class="form-input"
            clearable
            filterable
            v-model="company.city"
            :placeholder="$t('register.form.select')">
            <el-option
              v-for="item in cityOptions"
              :key="item.key"
              :label="item.value"
              :value="item.key + '_' + item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.postCode')" prop="postalCode">
          <el-input
            class="form-input"
            clearable
            v-model="company.postalCode"
            max="5"
            maxlength="5" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.npwpCity')" prop="npwpCity">
          <el-select
            class="form-input"
            clearable
            filterable
            v-model="company.npwpCity"
            :placeholder="$t('register.form.select')">
            <el-option
              v-for="item in cityOptionsNpwp"
              :key="item.key"
              :label="item.value"
              :value="item.key + '_' + item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.address.npwpPostCode')" prop="npwpPostalCode">
          <el-input
            class="form-input"
            clearable
            v-model="company.npwpPostalCode"
            max="5"
            maxlength="5" />
        </el-form-item>
      </el-col>
    </el-row>


    <el-divider content-position="left"><h4>{{ $t('register.basic.contact.title') }}</h4></el-divider>
    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.contact.phone')" prop="phone" required>
          <el-input
            class="form-input"
            clearable
            v-model="company.phone" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.contact.fax')" prop="fax">
          <el-input
            class="form-input"
            clearable
            v-model="company.fax" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.contact.email')" prop="email" required>
          <el-input
            class="form-input"
            clearable
            v-model="company.email" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item :label="$t('register.basic.contact.website')" prop="website">
          <el-input
            class="form-input"
            clearable
            v-model="company.website" />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts" src="./company-profile.component.ts"></script>
<style lang="scss">
  .form-input {
    textarea {
      resize: none;
      border: none;
      border-radius: 0px;
      border-bottom: 1px solid #bfcbd9;
    }
  }

  /*
  .el-form-item {
    margin-bottom: 0px;
  }

  .el-form-item--mini.el-form-item {
    margin-bottom: 0px;
  }
  */
</style>
