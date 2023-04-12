<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="district">
        <h2 class="jh-entity-heading" data-cy="districtDetailsHeading">
          <span v-text="$t('elonlarApp.district.detail.title')">District</span> {{ district.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('elonlarApp.district.nameUzLat')">Name Uz Lat</span>
          </dt>
          <dd>
            <span>{{ district.nameUzLat }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.district.nameUzKr')">Name Uz Kr</span>
          </dt>
          <dd>
            <span>{{ district.nameUzKr }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.district.nameRu')">Name Ru</span>
          </dt>
          <dd>
            <span>{{ district.nameRu }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.district.nameEn')">Name En</span>
          </dt>
          <dd>
            <span>{{ district.nameEn }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.district.longitude')">Longitude</span>
          </dt>
          <dd>
            <span>{{ district.longitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.district.latitude')">Latitude</span>
          </dt>
          <dd>
            <span>{{ district.latitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.district.region')">Region</span>
          </dt>
          <dd>
            <div v-if="district.region">
              <router-link :to="{ name: 'RegionView', params: { regionId: district.region.id } }">{{ district.region.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="district.id" :to="{ name: 'DistrictEdit', params: { districtId: district.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./district-details.component.ts"></script>
