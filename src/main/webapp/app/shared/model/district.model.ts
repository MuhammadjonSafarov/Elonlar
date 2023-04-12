import { IRegion } from '@/shared/model/region.model';

export interface IDistrict {
  id?: number;
  nameUzLat?: string | null;
  nameUzKr?: string | null;
  nameRu?: string | null;
  nameEn?: string | null;
  longitude?: number | null;
  latitude?: number | null;
  region?: IRegion | null;
}

export class District implements IDistrict {
  constructor(
    public id?: number,
    public nameUzLat?: string | null,
    public nameUzKr?: string | null,
    public nameRu?: string | null,
    public nameEn?: string | null,
    public longitude?: number | null,
    public latitude?: number | null,
    public region?: IRegion | null
  ) {}
}
