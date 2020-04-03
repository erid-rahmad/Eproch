<template>
  <el-form
    ref="company"
    label-position="left"
    label-width="128px"
    :model="company"
    :rules="rules"
  >
    <el-row :gutter="columnSpacing">
      <el-col :span="12">
        <el-form-item label="Name" prop="name" required>
          <el-input v-model="company.name" />
        </el-form-item>
        <el-form-item label="Type" prop="type" required>
          <el-select v-model="company.type" placeholder="Select">
            <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="branch">
          <el-checkbox
            v-model="company.branch"
            label="Branch"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="NPWP" prop="npwp" required>
          <el-input v-model="company.npwp" />
        </el-form-item>
        <el-form-item label="NPWP Name" prop="npwpName" required>
          <el-input v-model="company.npwpName" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-divider content-position="left">Address Information</el-divider>
    <el-row :gutter="columnSpacing">
      <el-col :span="12">
        <el-form-item label="Address" prop="address" required>
          <el-input v-model="company.address" type="textarea" :autosize="{ maxRows: 3 }" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="NPWP Address" prop="npwpAddress" required>
          <el-input v-model="company.npwpAddress" type="textarea" :autosize="{ maxRows: 3 }" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item label="Country" prop="country" required>
          <el-select v-model="company.country" placeholder="Select Country">
            <el-option
              v-for="item in countryOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Region" prop="region">
          <el-select v-model="company.region" placeholder="Select Region">
            <el-option
              v-for="item in regionOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="NPWP Country" prop="npwpCountry" required>
          <el-select v-model="company.npwpCountry" placeholder="Select Country">
            <el-option
              v-for="item in countryOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="NPWP Region" prop="npwpRegion">
          <el-select v-model="company.npwpRegion" placeholder="Select Region">
            <el-option
              v-for="item in regionOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item label="City" prop="city" required>
          <el-select v-model="company.city" placeholder="Select City">
            <el-option
              v-for="item in cityOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Postal Code" prop="postalCode">
          <el-input v-model="company.postalCode" max="5" maxlength="5" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="NPWP City" prop="npwpCity" required>
          <el-select v-model="company.npwpCity" placeholder="Select City">
            <el-option
              v-for="item in cityOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="NPWP Postal Code" prop="npwpPostalCode">
          <el-input v-model="company.npwpPostalCode" max="5" maxlength="5" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-divider content-position="left">Contact Information</el-divider>
    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item label="Phone" prop="phone" required>
          <el-input v-model="company.phone" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Fax" prop="fax">
          <el-input v-model="company.fax" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Email" prop="email" required>
          <el-input v-model="company.email" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Website" prop="website">
          <el-input v-model="company.website" />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts">
import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'

const CompanyProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    company: {
      type: Object,
      default: () => {}
    }
  }
})

@Component
export default class CompanyProfile extends CompanyProps {
  data() {
    return {
      columnSpacing: 32,
      rules: {
        website: {
          type: 'url'
        }
      },
      typeOptions: [{
        label: 'Company',
        value: 'C'
      }, {
        label: 'Professional',
        value: 'P'
      }],
      countryOptions: [{
        label: 'Indonesia',
        value: 'ID'
      }, {
        label: 'Malaysia',
        value: 'MY'
      }, {
        label: 'Singapore',
        value: 'SG'
      }],
      regionOptions: [{
        label: 'DKI Jakarta',
        value: 'JKT'
      }, {
        label: 'Jawa Barat',
        value: 'JBR'
      }, {
        label: 'Jawa Tengah',
        value: 'JTG'
      }],
      cityOptions: [
        'Jakarta Barat',
        'Jakarta Pusat',
        'Jakarta Selatan',
        'Jakarta Timur',
        'Jakarta Utara',
        'Kep. Seribu'
      ]
    }
  }

  mounted() {
    this.eventBus.$on('validate-form', this.validate)
  }

  validate(formIndex) {
    if (formIndex === 1) {
      (this.$refs.company as ElForm).validate((passed, errors) => {
        this.eventBus.$emit('step-validated', { passed, errors })
      })
    }
  }
}
</script>
