<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="region">
        <h2 class="jh-entity-heading" data-cy="regionDetailsHeading">
          <span v-text="$t('elonlarApp.region.detail.title')">Region</span> {{ region.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('elonlarApp.region.nameUzLat')">Name Uz Lat</span>
          </dt>
          <dd>
            <span>{{ region.nameUzLat }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.region.nameUzKr')">Name Uz Kr</span>
          </dt>
          <dd>
            <span>{{ region.nameUzKr }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.region.nameRu')">Name Ru</span>
          </dt>
          <dd>
            <span>{{ region.nameRu }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.region.nameEn')">Name En</span>
          </dt>
          <dd>
            <span>{{ region.nameEn }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.region.longitude')">Longitude</span>
          </dt>
          <dd>
            <span>{{ region.longitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.region.latitude')">Latitude</span>
          </dt>
          <dd>
            <span>{{ region.latitude }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="region.id" :to="{ name: 'RegionEdit', params: { regionId: region.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./region-details.component.ts"></script>
