<template>
  <div class="comparison-window">
    <el-row
      class="comparison-table"
      :gutter="16"
    >
      <el-col :span="4">
        <div class="empty-block">&nbsp;</div>
        <el-table
          :data="infoLabels"
          :show-header="false"
          size="small"
        >
          <el-table-column>
            <template slot-scope="{ row }">
              {{ row }}
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="20">
        <el-row :gutter="24">
          <el-col
            v-for="(item, index) in items"
            :key="item.id"
            class="compare-block"
            :span="colSpan"
          >
            <div class="product-search">
              <el-autocomplete
                v-model="searchCriteria[index]"
                clearable
                :fetch-suggestions="(value, callback) => searchItems(value, callback)"
                placeholder="Search product"
                size="small"
                @select="item => selectItem(item, index)"
              >
                <el-button
                  slot="append"
                  icon="el-icon-delete"
                  size="mini"
                  type="danger"
                  @click="removeItem(item)"
                />
              </el-autocomplete>
            </div>
            <div class="product-image">
              <el-image :src="getImage(item)"/>
            </div>
            <div class="product-info">
              <el-table
                :data="item.info"
                :show-header="false"
                size="small"
              >
                <el-table-column>
                  <template slot-scope="{ row }">
                    <span v-if="row.filter === 'currency'">Rp. {{ row.content | formatCurrency('id') }}</span>
                    <div v-else-if="row.html" v-html="row.content"></div>
                    <span v-else>{{ row.content }}</span>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
          <el-col
            v-if="canAddSlot"
            :span="colSpan"
          >
            <div class="product-search">
              <el-autocomplete
                v-model="newSearch"
                :fetch-suggestions="(value, callback) => searchItems(value, callback)"
                size="small"
                placeholder="Search product"
                @select="item => selectItem(item)"
              />
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./comparison-window.component.ts"></script>
<style lang="scss">
.comparison-window {
  .empty-block {
    height: 198px;
    width: 100%;
  }

  .compare-block {
    border-left: 1px solid #d0d0d6;

    &:nth-last-child(1) {
      border-left: none;
    }

    &:nth-last-child(2) {
      border-right: 1px solid #d0d0d6;
    }
  }
}
</style>
