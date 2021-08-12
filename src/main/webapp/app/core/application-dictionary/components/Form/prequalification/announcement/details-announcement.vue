<template>
  <div class="bidding-invitation-response">
    <h3 style="margin-top: 0">Prequalification Invitation Details</h3>
    <div class="header">
      <h2>{{ moreinfo.documentNo }}</h2>
      <p>{{ moreinfo.name }}</p>
    </div>
    <el-row>
      <el-col
        :xs="24"
        :sm="20"
        :md="18"
        :lg="16"
        :xl="12"
      >
        <div>
          <el-divider content-position="left">
            <h4>Interested Vendors</h4>
          </el-divider>
          <el-table
            border
            :data="vendorJoin"
            highlight-current-row
            size="mini"
            stripe
          >
            <el-table-column width="50" label="No">
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="Vendor Name" min-width="200" prop="vendorName" sortable show-overflow-tooltip></el-table-column>
            <el-table-column label="Joined Date" width="150" sortable>
              <template slot-scope="{ row }">
                {{ row.answerDate | formatDate }}
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div>
          <el-divider content-position="left">
            <h4>Uninterested Vendors</h4>
          </el-divider>
          <el-table
            border
            :data="vendorNotJoin"
            highlight-current-row
            size="mini"
            stripe
          >
            <el-table-column width="50" label="No">
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column min-width="200" sortable prop="vendorName" label="Vendor Name" show-overflow-tooltip></el-table-column>
            <el-table-column width="150" sortable label="Refusal Date">
              <template slot-scope="{ row }">
                {{ row.answerDate | formatDate }}
              </template>
            </el-table-column>
            <el-table-column min-width="200" sortable prop="reason" label="Reason" />
          </el-table>
        </div>
        <div>

          <el-divider content-position="left">
              <h4>Vendors who have downloaded the documents</h4>
          </el-divider>
          <el-table
            border
            :data="vendorDownload"
            highlight-current-row
            size="mini"
            stripe
          >
              <el-table-column width="50" label="No">
                  <template slot-scope="row">
                      {{ row.$index + 1 }}
                  </template>
              </el-table-column>
              <el-table-column min-width="200" sortable prop="vendorName" label="Vendor Name"></el-table-column>
          </el-table>
        </div>
        <div>
          <el-divider content-position="left">
            <h4>Not yet Responding</h4>
          </el-divider>
          <el-table
            border
            :data="vendorNoResponse"
            highlight-current-row
            size="mini"
            stripe
          >
            <el-table-column width="50" label="No">
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column min-width="200" sortable prop="vendorName" label="Vendor Name" show-overflow-tooltip></el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./details-announcement.component.ts"></script>
<style lang="scss">
.compact .bidding-invitation-response {
  .el-table--mini {
    td,
    th {
      height: 35px;
    }
  }
}

.header {
  text-align: center;
  font-size: 15px;
}
</style>
<style lang="scss" scoped>
.bidding-invitation-response {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
