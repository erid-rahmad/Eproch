<template>
  <div class="watch-list">
    <el-row
      v-if="isCard"
      :gutter="40"
      class="panel-group"
    >
      <el-col
        v-for="item in items"
        :key="item.id"
        :md="12"
        :lg="8"
        :xl="6"
        class="card-panel-col"
      >
        <div
          class="card-panel"
          @click="onCardClicked(item)"
        >
          <div class="card-panel-icon-wrapper">
            <svg-icon
              :name="item.icon"
              class="card-panel-icon"
            />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              {{ item.name }}
            </div>
            <count-to
              :start-val="0"
              :end-val="item.count"
              class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
    </el-row>
    <el-table
      v-if="isList"
      :data="items"
      :show-header="false"
      stripe
      style="width: 256px"
    >
      <el-table-column
        prop="count"
        width="64"
      />
      <el-table-column>
        <template slot-scope="{row}">
          <div @click="onCardClicked(row)">
            {{row.name}}
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" src="./watch-list.component.ts"></script>
<style lang="scss" scoped>
.panel-group {
  margin-bottom: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        /* color: #fff; */
      }

      .icon-people {
         background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
