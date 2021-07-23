<template>
  <div class="bidding-negotiation-line-conversation">
    <el-form
      ref="line"
      label-position="left"
      label-width="200px"
      :model="line"
      size="mini"
    >
      <el-row
        :gutter="24"
        style="margin-top: 16px"
      >
        <el-col
          :xs="24"
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Vendor">
            <el-input
              v-model="line.vendorName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="" v-if="chatHistory.length && (line.negotiationStatus!='agreed'&&line.negotiationStatus!='disagreed')">
            <el-button
              size="mini"
              @click="viewNegoDetail"
            >
              View Detail
            </el-button>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item>
            &nbsp;
          </el-form-item>
          <el-form-item label="" v-if="(line.negotiationStatus!='agreed'&&line.negotiationStatus!='disagreed')">
            <el-button
              size="mini"
              @click="openChatForm"
            >
              Add New
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row
        :gutter="24"
        style="margin-top: 16px"
      >
      
      <el-skeleton :loading="loading" animated :count="3">
        <template slot="template">
          <div class="card" style="border: solid; border-width: thin;">
            <el-skeleton-item variant="h3" style="width: 30%"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text" style="width: 65%"/>
          </div>
        </template>
        <template>
          <div class="form-wrapper card-view app-container" v-if="chatHistory.length" style="background:#FFFFFF">
            <div class="card" v-for="(c,index) in chatHistory" :key="index" style="border: solid; border-width: thin;">
              <h4>{{c.vendorText?line.vendorName:"Buyer"}}
                <el-button
                  class="btn-attachment"
                  icon="el-icon-download"
                  size="mini"
                  type="primary"
                  v-if="c.attachmentId"
                  @click="downloadAttachment(c)"
                >
                </el-button>
              </h4>
              <p>{{c.vendorText?c.vendorText:c.buyerText}}</p>
            </div>
          </div>
          <div class="form-wrapper" v-else style="text-align: center;">
            <p>Tidak ada percakapan</p>
          </div>
        </template>
      </el-skeleton>
      <!--
      <el-table border :data="chatHistory" size="mini">
        <el-table-column width="100" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="From" width="200">
          <template slot-scope="{row}">
            <div v-if="row.vendorText">Vendor</div>
            <div v-else>Buyer</div>
          </template>
        </el-table-column>
        <el-table-column label="Content" width="750">
          <template slot-scope="{row}">
            <div v-if="row.vendorText">{{row.vendorText}}</div>
            <div v-else>{{row.buyerText}}</div>
          </template>
        </el-table-column>
        <el-table-column label="Attachment" width="400">
          <template slot-scope="{row}">
            <el-button
              class="btn-attachment"
              icon="el-icon-download"
              size="mini"
              type="primary"
              v-if="row.attachmentId"
              @click="downloadAttachment(row)"
            >
              Download
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      -->
      </el-row>
    </el-form>

    <el-dialog
      width="80%"
      :visible.sync="showNegoForm"
    >
      <el-form
        ref="negoForm"
        label-position="left"
        label-width="96px"
        size="mini"
      >
        <el-row
          :gutter="24"
          style="margin-top: 16px"
        >
          <el-col
            :span="8"
          >
            <el-form-item label="Bidding No">
              <el-input
                v-model="line.biddingNo"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="8"
          >
            <el-form-item label="Bidding Type">
              <el-input
                v-model="line.biddingType"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="8"
          >
            <el-form-item label="Submission Price">
              <el-input
                v-model="line.proposedPrice"
                v-inputmask="{'alias': 'currency'}"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row
          :gutter="24"
          style="margin-top: 16px"
        >
          <el-col
            :span="8"
          >
            <el-form-item label="Bidding Title">
              <el-input
                v-model="line.biddingTitle"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="8"
          >
            <el-form-item label="Vendor">
              <el-input
                v-model="line.vendorName"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="8"
          >
            <el-form-item label="Negotiation Price">
              <el-input
                v-model="negoPrice.negotiationPrice"
                v-inputmask="{'alias': 'currency'}"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row
          :gutter="24"
          style="margin-top: 16px"
        >
          <el-col
            :span="8"
          >
            <el-form-item label="Attachment">
              <el-upload
                ref="contractFile"
                v-model="negoPrice.attachment"
                :action="action"
                class="upload-demo"
                :limit="limit"
                :multiple="false"
                :accept="accept"
                :file-list="fileList2"
                :before-upload="handleBeforeUpload"
                :on-change="onUploadChangeN"
                :on-preview="handlePreview"
                :on-exceed="handleExceed"
                :on-remove="handleRemoveN"
                :on-error="onUploadError"
                :on-success="onUploadSuccessN"
              >
                <el-button
                  size="mini"
                  type="primary"
                >
                  Select Document
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col
            :span="8"
          >
            &nbsp;
          </el-col>
          <el-col
            :span="8"
          >
            <el-form-item label="Price Difference Percentage" v-if="!isVendor">
              <el-input
                v-model="negoPrice.percentDiff"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-table border :data="negoPriceLine" size="mini" v-loading="detailLoading">
          <el-table-column width="60" label="No.">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="Product" prop="productName" width="200">
          </el-table-column>
          <el-table-column label="Sub Item" prop="subItemName" width="200">
          </el-table-column>
          <el-table-column label="Quantity" prop="quantity" width="60">
          </el-table-column>
          <el-table-column label="Uom" prop="uomName" width="60">
          </el-table-column>
          <el-table-column label="Ceiling Price / Unit" width="200" align="right">
            <template slot-scope="{row}">
              {{row.ceilingPrice | formatCurrency}}
            </template>
          </el-table-column>
          <el-table-column label="Total Ceiling Price" width="200" align="right">
            <template slot-scope="{row}">
              {{row.totalCeilingPrice | formatCurrency}}
            </template>
          </el-table-column>
          <el-table-column label="Submission Price / Unit" width="200" align="right">
            <template slot-scope="{row}">
              {{row.proposedPrice | formatCurrency}}
            </template>
          </el-table-column>
          <el-table-column label="Total Submission Price" width="200" align="right">
            <template slot-scope="{row}">
              {{row.totalPriceSubmission | formatCurrency}}
            </template>
          </el-table-column>
          <el-table-column label="Negotiation Price / Unit" v-if="isVendor" width="200" align="right">
            <template slot-scope="{row}">
              {{row.priceNegotiation | formatCurrency}}
            </template>
          </el-table-column>
          <el-table-column label="Negotiation Price / Unit" v-else width="200">
            <template slot-scope="{row}">
              <el-input 
                v-model="row.priceNegotiation" 
                v-inputmask="{'alias': 'currency'}" 
                v-on:change="updateTotal(row)" 
                :disabled="isPercentage">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="Negotiation Percentage (%)" prop="negotiationPercentage" v-if="isVendor" width="200">
          </el-table-column>
          <el-table-column label="Negotiation Percentage (%)" v-else width="200">
            <template slot-scope="{row}">
              <el-input 
                v-model="row.negotiationPercentage" 
                v-on:change="updateTotalByPercentage(row)" 
                :disabled="!isPercentage">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="Total Negotiation Price" prop="totalNegotiationPrice" width="200" align="right">
            <template slot-scope="{row}">
              {{row.totalNegotiationPrice | formatCurrency}}
            </template>
          </el-table-column>
        </el-table>
        <el-form-item prop="percentCheck" v-if="!isVendor">
          <el-checkbox
            v-model="isPercentage"
          >Generate Percentage?</el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="showNegoForm=false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-close"
          size="mini"
          type="danger"
          @click="declineNegotiation"
          v-if="isVendor"
        >
          Disagree
        </el-button>
        <el-button
          icon="el-icon-check"
          :loading="submitting"
          size="mini"
          type="primary"
          @click="submitNegotiation"
        >
          {{isVendor?"Agree":"Submit"}}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="80%"
      :visible.sync="showChatForm"
      :before-close="clearForm"
    >
      <el-form
        ref="chatForm"
        :rules="chatFormValidationSchema"
        label-position="left"
        label-width="96px"
        :model="chatForm"
        size="mini"
      >
        <el-row
          :gutter="24"
          style="margin-top: 16px"
        >
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Bidding Title">
              <el-input
                v-model="line.biddingTitle"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Attachment">
              <el-upload
                ref="contractFile"
                v-model="chatForm.attachment"
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
        </el-row>
        <el-form-item prop="text">
          <el-input
            v-model="chatForm.text"
            :rows="7"
            type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item prop="publishCheck">
          <el-checkbox
            v-model="chatForm.publishToEmail"
          > Publish to Email?</el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="clearForm"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          :loading="submitting"
          size="mini"
          type="primary"
          @click="submitForm"
        >
          Submit
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./line-conversation.component.ts"></script>

<style lang="scss">
.compact {
  .bidding-negotiation-line-conversation {
    .el-table--mini {
      th, td {
        height: 35px;
      }
    }
  }
}

.bidding-negotiation-line-conversation {
  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }
}

p {
  font-size: 14px;
}
</style>
