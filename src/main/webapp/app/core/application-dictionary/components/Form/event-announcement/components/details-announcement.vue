<template>
  <div class="details-announcement">
    <el-row>
      <!--            <el-row :gutter="columnSpacing">-->
      <!--                <el-col :span="24">-->
      <!--                    <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">-->
      <!--                        Back-->
      <!--                    </el-button>-->
      <!--                </el-col>-->
      <!--            </el-row>-->
      <el-col :span="24">
        <el-divider content-position="left">
          <h4>Pendaftaran dan Penyampaian Dokumen Pengadaan</h4>
        </el-divider>
        <div class="header">
          <h2>{{ moreinfo.biddingDocNo }}</h2>
          <p>{{ moreinfo.biddingName }}</p>
        </div>
        <div>
          <el-divider content-position="left">
            <h4>Penyedia yang Sudah Mendaftar</h4>
          </el-divider>
          <el-table :data="vendorJoin" style="width: 80%" size="mini">
            <el-table-column width="50" label="No">
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column min-width="80" sortable prop="vendorName" label="Nama Perusahaan" />
            <el-table-column min-width="100" sortable label="Tanggal Daftar">
              <template slot-scope="{ row }">
                <span>{{ formattime(row.answerDate) }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div>
          <el-divider content-position="left">
            <h4>Penyedia yang Menolak</h4>
          </el-divider>
          <el-table :data="vendorNotJoin" style="width: 80%" size="mini">
            <el-table-column width="50" label="No">
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column min-width="80" sortable prop="vendorName" label="Nama Perusahaan" />
            <el-table-column min-width="100" sortable label="Tanggal Tolak">
              <template slot-scope="{ row }">
                <span>{{ formattime(row.answerDate) }}</span>
              </template>
            </el-table-column>
            <el-table-column min-width="100" sortable prop="reason" label="Alasan" />
          </el-table>
            <el-divider content-position="left">
                <h4>Penyedia yang Download</h4>
            </el-divider>
            <el-table :data="vendorDownload" style="width: 80%" size="mini">
                <el-table-column width="50" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column min-width="80" sortable prop="vendorName" label="Nama Perusahaan" />
            </el-table>
        </div>
        <div>
          <el-divider content-position="left">
            <h4>Penyedia yang Tidak Menjawab</h4>
          </el-divider>
          <el-table :data="vendorNoResponse" style="width: 80%" size="mini">
            <el-table-column width="50" label="No">
              <template slot-scope="row">
                {{ row.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column min-width="80" sortable prop="vendorName" label="Nama Perusahaan" />
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./details-announcement.component.ts"></script>
<style lang="scss">
.compact .details-announcement {
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
.details-announcement {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
