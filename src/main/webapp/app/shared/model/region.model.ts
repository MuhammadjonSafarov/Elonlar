export interface IRegion {
  id?: number;
  nameUzLat?: string | null;
  nameUzKr?: string | null;
  nameRu?: string | null;
  nameEn?: string | null;
  longitude?: number | null;
  latitude?: number | null;
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public nameUzLat?: string | null,
    public nameUzKr?: string | null,
    public nameRu?: string | null,
    public nameEn?: string | null,
    public longitude?: number | null,
    public latitude?: number | null
  ) {}
}
