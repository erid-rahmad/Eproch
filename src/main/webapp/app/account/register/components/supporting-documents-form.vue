<template>
    <el-form
        ref="document"
        label-position="left"
        label-width="128px"
        :model="document"
        :rules="rules"
    >
        <el-form-item label="Document Type" prop="docType" required>
            <el-select v-model="document.docType" placeholder="Select Document Type">
                <el-option
                    v-for="item in documentTypes"
                    :key="item"
                    :label="item"
                    :value="item"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="Document No." prop="docNo" required>
            <el-input v-model="document.docNo" />
        </el-form-item>
        <el-form-item label="Expiration Date" prop="expirationDate">
            <el-date-picker
                ref="documentExpirationDate"
                v-model="document.expirationDate"
                type="date"
                format="dd/MM/yyyy"
                placeholder="Pick a day"
            />
        </el-form-item>
    </el-form>
</template>

<script lang="ts">
import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'

const LoginProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    document: {
      type: Object,
      default: () => {
          return {
              docType: '',
              docNo: '',
              expirationDate: '',
              file: null
          }
      }
    }
  }
})

@Component
export default class SupportingDocumentsForm extends LoginProps {
    private documentTypes = ['SIUP', 'SIUJK'];
    private rules = {
        
    }

    mounted() {
        this.eventBus.$on('save-document', this.save);
    }

    save() {
        setTimeout(() => {
            const data = {...this.document};
            this.eventBus.$emit('push-document', data);
            (<ElForm>this.$refs.document).resetFields();
        }, 1000);
    }
}
</script>
