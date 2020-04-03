<template>
    <div>
        <h4>{{ $t('register.document.mandatory.title') }}</h4>
        <p>{{ $t('register.document.mandatory.description') }}</p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button type="primary" icon="el-icon-plus" circle @click.native.prevent="addDocument('mainDocuments')" />
            </div>
            <el-table
                ref="mainDocuments"
                :data="mainDocuments"
                height="250"
                style="width: 100%">
                <el-table-column
                    prop="docType"
                    label="Document Type"
                />
                <el-table-column
                    prop="docNo"
                    label="Document No."
                />
                <el-table-column
                    prop="expirationDate"
                    label="Expiration Date"
                />
                <el-table-column
                    prop="file"
                    label="File"
                />
            </el-table>
        </div>
        <h4>{{ $t('register.document.additional.title') }}</h4>
        <p>{{ $t('register.document.additional.description') }}</p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button type="primary" icon="el-icon-plus" circle @click.native.prevent="addDocument('additionalDocuments')" />
            </div>
            <el-table
                ref="additionalDocuments"
                :data="additionalDocuments"
                height="250"
                style="width: 100%">
                <el-table-column
                    prop="docType"
                    label="Document Type"
                />
                <el-table-column
                    prop="docNo"
                    label="Document No."
                />
                <el-table-column
                    prop="expirationDate"
                    label="Expiration Date"
                />
                <el-table-column
                    prop="file"
                    label="File"
                />
            </el-table>
        </div>
        <el-dialog :title="$t('register.form.document[\'title.edit\']')" :visible.sync="editDialogVisible">
            <el-row :gutter="16">
                <el-col :span="18" :offset="3">
                    <supporting-documents-form
                        :event-bus="eventBus"
                        :document="document"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">Cancel</el-button>
                <el-button :loading="loading" type="primary" @click="saveDocument">Save</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import SupportingDocumentsForm from './supporting-documents-form.vue';

const DocumentProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    mainDocuments: {
      type: Array,
      default: () => []
    },
    additionalDocuments: {
        type: Array,
        default: () => []
    }
  }
})

@Component({
    components: {
        SupportingDocumentsForm
    }
})
export default class SupportingDocuments extends DocumentProps {
    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    document = {};
    editingForm = null;

    mounted() {
        this.eventBus.$on('push-document', (document) => {
            this[this.editingForm].push(document);
            this.loading = false;
            this.editDialogVisible = false;
        });
    }

  addDocument(target: string) {
      this.editingForm = target;
      this.editDialogVisible = true;
  }

  saveDocument() {
      this.loading = true;
      this.eventBus.$emit('save-document');
  }
}
</script>

<style lang="scss">
.table-container {
    border: 1px solid #ebebeb;
    border-radius: 3px;
    margin-bottom: 24px;
    padding: 24px;
    position: relative;
    transition: .25s;

    &:hover {
        box-shadow: 0 0 8px 0 rgba(232,237,250,.6), 0 2px 4px 0 rgba(232,237,250,.5);
    }

    .toolbar {
        &.float-top-right {
            position: absolute;
            right: 24px;
            top: -48px;
        }

        .el-button--primary {
            box-shadow: 0 0 8px 0 rgba(232,237,250,.6), 0 2px 4px 0 rgba(232,237,250,.5);
        }
    }
}
</style>
